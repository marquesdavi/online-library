-- Table: image_table
CREATE TABLE IF NOT EXISTS image_table (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           image LONGBLOB,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: book
CREATE TABLE IF NOT EXISTS book (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255),
                                    description VARCHAR(255),
                                    synopsis TEXT,
                                    author VARCHAR(255),
                                    category VARCHAR(255),
                                    code VARCHAR(255),
                                    image_id INT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (image_id) REFERENCES image_table(id)
);

-- Table: book_borrow
CREATE TABLE IF NOT EXISTS book_borrow (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           book_id INT,
                                           user_id INT,
                                           borrow_date TIMESTAMP,
                                           expected_return_date DATE,
                                           return_date TIMESTAMP,
                                           is_late BOOLEAN,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           FOREIGN KEY (book_id) REFERENCES book(id),
                                           FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table: users
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255),
                                     email VARCHAR(255),
                                     password VARCHAR(255),
                                     first_name VARCHAR(255),
                                     last_name VARCHAR(255)
);

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255)
);

-- Table: users_roles
CREATE TABLE IF NOT EXISTS users_roles (
                                           user_id INT,
                                           role_id INT,
                                           PRIMARY KEY (user_id, role_id),
                                           FOREIGN KEY (user_id) REFERENCES users(id),
                                           FOREIGN KEY (role_id) REFERENCES roles(id)
);
