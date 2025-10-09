
-- Seed users: use UPSERT so running the seed updates existing rows when email conflicts.
-- This ensures edits to this file are applied on application startup.


INSERT INTO users (name, email, password) VALUES ('Alice', 'alice@example.com', '$2a$10$/8ZvPnxQiTN0jT.AepSj1uclXBkJ4rHMgxpYqf9eZnwFGr3AWrYNu')
	ON CONFLICT(email) DO UPDATE SET name=excluded.name, password=excluded.password;


INSERT INTO users (name, email, password) VALUES ('moniodom', 'moniodom@example.com', '$2a$10$L7xbBE4UU3RWcgsvPvJjJ.suG39gAbG4Pu8bMvhklRk0WukiTV4GG')
	ON CONFLICT(email) DO UPDATE SET name=excluded.name, password=excluded.password;

-- seed students (update name when email conflicts)
INSERT INTO students (name, email) VALUES ('Student A', 'studenta@example.com')
	ON CONFLICT(email) DO UPDATE SET name=excluded.name;

INSERT INTO students (name, email) VALUES ('Student B', 'studentb@example.com')
	ON CONFLICT(email) DO UPDATE SET name=excluded.name;

INSERT INTO students (name, email) VALUES ('Student C', 'studentc@example.com')
	ON CONFLICT(email) DO UPDATE SET name=excluded.name;
