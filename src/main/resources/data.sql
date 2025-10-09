-- Seed users: use explicit columns (no id) so AUTOINCREMENT handles ids.
-- INSERT OR IGNORE is used so running the seed multiple times won't create duplicates
-- because the `email` column is UNIQUE in the schema.

INSERT OR IGNORE INTO users (name, email, password) VALUES ('Alice', 'alice@example.com', 'secret');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Bob', 'bob@example.com', 'password1');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Carol', 'carol@example.com', 'password2');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('David', 'david@example.com', 'password3');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Eve', 'eve@example.com', 'password4');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Frank', 'frank@example.com', 'password5');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Grace', 'grace@example.com', 'password6');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Heidi', 'heidi@example.com', 'password7');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Ivan', 'ivan@example.com', 'password8');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Judy', 'judy@example.com', 'password9');
INSERT OR IGNORE INTO users (name, email, password) VALUES ('Mallory', 'mallory@example.com', 'password10');

-- seed students
INSERT OR IGNORE INTO students (name, email) VALUES ('Student A', 'studenta@example.com');
INSERT OR IGNORE INTO students (name, email) VALUES ('Student B', 'studentb@example.com');
INSERT OR IGNORE INTO students (name, email) VALUES ('Student C', 'studentc@example.com');
