#!/bin/bash

# wait-for-it.sh
# Waits for the specified host and port to be available before executing a command.

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

timeout=30
strict=false
quiet=false

while [[ $# -gt 0 ]]; do
  case "$1" in
    --strict)
      strict=true
      shift 1
      ;;
    -s)
      strict=true
      shift 1
      ;;
    --quiet)
      quiet=true
      shift 1
      ;;
    -q)
      quiet=true
      shift 1
      ;;
    -t)
      timeout="$2"
      if [[ ! "$timeout" =~ ^[0-9]+$ ]]; then
        echo "Invalid timeout value: $timeout"
        exit 1
      fi
      shift 2
      ;;
    --timeout=*)
      timeout="${1#*=}"
      if [[ ! "$timeout" =~ ^[0-9]+$ ]]; then
        echo "Invalid timeout value: $timeout"
        exit 1
      fi
      shift 1
      ;;
    --)
      shift
      break
      ;;
    --help)
      echo "wait-for-it.sh - wait for a service to be available before executing a command."
      echo
      echo "The script will check the availability of the given host and port before executing the command."
      echo "The default timeout is 30 seconds."
      echo
      echo "Usage:"
      echo "  wait-for-it.sh host:port [-s] [-t timeout] [-- command args]"
      echo
      echo "Options:"
      echo "  -h HOST | --host=HOST       Host or IP under test"
      echo "  -p PORT | --port=PORT       TCP port under test"
      echo "                              Alternatively, you specify the host and port as host:port"
      echo "  -s | --strict               Only execute subcommand if the test succeeds"
      echo "  -q | --quiet                Don't output any status messages"
      echo "  -t TIMEOUT | --timeout=TIMEOUT"
      echo "                              Timeout in seconds, zero for no timeout"
      echo "  -- COMMAND ARGS             Execute command with args after the test finishes"
      exit 0
      ;;
    *)
      echo "Unknown argument: $1"
      exit 1
      ;;
  esac
done

# Split host and port
IFS=':' read -r -a host_port <<< "$host"
host="${host_port[0]}"
port="${host_port[1]}"

if [ "$quiet" = false ]; then
  echo "Waiting for $host:$port..."
fi

start_ts=$(date +%s)
while ! nc -z "$host" "$port" &>/dev/null; do
  sleep 1
  current_ts=$(date +%s)
  if [ "$quiet" = false ]; then
    echo -n "."
  fi
  if [ "$((current_ts - start_ts))" -gt "$timeout" ]; then
    if [ "$quiet" = false ]; then
      echo " Timeout reached."
    fi
    exit 1
  fi
done

if [ "$quiet" = false ]; then
  echo " Service is available!"
fi

if [ "$strict" = true ]; then
  exec "$cmd"
else
  exec "$@"
fi
