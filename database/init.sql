CREATE TABLE IF NOT EXISTS book(
    id varchar(100) PRIMARY KEY,
    title text NOT NULL,
    description text NOT NULL,
    author text NOT NULL,
    category text NOT NULL,
    code text NOT NULL,
    created_at text NOT NULL
);