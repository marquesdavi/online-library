package br.com.online.library.util;

import java.util.HashMap;
import java.util.Random;

public class CodeGenerator {
    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_LIMIT = 10;
    private static final int BINARY_RANGE = 2;
    private static final int UPPER_CHAR_START = 65;
    private static final int UPPER_CHAR_LIMIT = 90;
    private static final int LOWER_CHAR_START = 97;
    private static final int LOWER_CHAR_LIMIT = 122;

    public static int randomNumber(String type) {
        var randomNum = new Random();
        int number;

        if (type.equalsIgnoreCase("binary")) {
            number = randomNum.nextInt(BINARY_RANGE);
        } else if (type.equalsIgnoreCase("character")) {
            if (randomNumber("binary") == 0) {
                number = randomNum.nextInt(UPPER_CHAR_LIMIT - UPPER_CHAR_START + 1) + UPPER_CHAR_START;
            } else {
                number = randomNum.nextInt(LOWER_CHAR_LIMIT - LOWER_CHAR_START + 1) + LOWER_CHAR_START;
            }
        } else {
            // Use a single-argument nextInt for the DEFAULT_START and DEFAULT_LIMIT case
            number = randomNum.nextInt(DEFAULT_LIMIT - DEFAULT_START + 1) + DEFAULT_START;
        }

        return number;
    }

    public static String newCode() {
        HashMap<Integer, String> codeDigits = new HashMap<>();

        for (int i = 0; i <= 10; i++){
            codeDigits.put(i, Character.toString(randomNumber("character")));
        }

        StringBuilder pattern = new StringBuilder("BK-");
        pattern.append(codeDigits.get(randomNumber("")));
        pattern.append(codeDigits.get(randomNumber("")));
        pattern.append(randomNumber(""));
        pattern.append(codeDigits.get(randomNumber("")));
        pattern.append(codeDigits.get(randomNumber("")));
        pattern.append(codeDigits.get(randomNumber("")));

        return pattern.toString();
    }
}
