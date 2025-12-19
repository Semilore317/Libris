-- This script initializes the database with mock data.


-- Clear existing data
DELETE FROM loan;
DELETE FROM reservation;
DELETE FROM book_instance;
DELETE FROM book;
DELETE FROM member;
DELETE FROM user_roles;
DELETE FROM roles;
DELETE FROM users;


-- Reset sequences for PostgreSQL
-- Reset sequences for PostgreSQL
-- ALTER SEQUENCE loan_id_seq RESTART WITH 1;
-- ALTER SEQUENCE reservation_id_seq RESTART WITH 1;
-- ALTER SEQUENCE book_instance_id_seq RESTART WITH 1;
-- ALTER SEQUENCE book_id_seq RESTART WITH 1;
-- ALTER SEQUENCE member_id_seq RESTART WITH 1;
-- ALTER SEQUENCE roles_id_seq RESTART WITH 1;
-- ALTER SEQUENCE users_id_seq RESTART WITH 1;


-- Create Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_LIBRARIAN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MEMBER');

-- Create Users
-- Passwords are bcrypt encoded for "password"
INSERT INTO users (id, username, password) VALUES (1, 'librarian', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');
INSERT INTO users (id, username, password) VALUES (2, 'member1', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');
INSERT INTO users (id, username, password) VALUES (3, 'member2', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');


-- Link Users to Roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

-- Create Members
INSERT INTO member (id, first_name, last_name, email, membership_number, user_id, joined_at) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'MEMBER-001', 2, '2025-01-15');
INSERT INTO member (id, first_name, last_name, email, membership_number, user_id, joined_at) VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com', 'MEMBER-002', 3, '2025-01-15');

-- Create Books
INSERT INTO book (id, title, author, isbn, publication_year, genre) VALUES (1, 'Dune', 'Frank Herbert', '978-0441013593', 1965, 'SCIENCE_FICTION');
INSERT INTO book (id, title, author, isbn, publication_year, genre) VALUES (2, '1984', 'George Orwell', '978-0451524935', 1949, 'FICTION');
INSERT INTO book (id, title, author, isbn, publication_year, genre) VALUES (3, 'The Hobbit', 'J.R.R. Tolkien', '978-0345339683', 1937, 'FANTASY');

-- Create Book Instances
-- 3 copies of Dune
INSERT INTO book_instance (id, book_id, status) VALUES (1, 1, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (2, 1, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (3, 1, 'ON_LOAN');
-- 2 copies of 1984
INSERT INTO book_instance (id, book_id, status) VALUES (4, 2, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (5, 2, 'ON_LOAN');
-- 1 copy of The Hobbit
INSERT INTO book_instance (id, book_id, status) VALUES (6, 3, 'ON_LOAN'); -- This one will be overdue

-- Create Loans
-- Active loan for Jane Smith
INSERT INTO loan (id, book_instance_id, member_id, borrowed_at, due_date) VALUES (1, 3, 2, '2025-11-20T10:00:00', '2025-12-20T23:59:59');
-- Active loan for John Doe
INSERT INTO loan (id, book_instance_id, member_id, borrowed_at, due_date) VALUES (2, 5, 1, '2025-12-01T14:00:00', '2025-12-22T23:59:59');
-- Overdue loan for John Doe
INSERT INTO loan (id, book_instance_id, member_id, borrowed_at, due_date) VALUES (3, 6, 1, '2025-11-01T11:00:00', '2025-12-01T23:59:59');

-- Create a reservation for an unavailable book
INSERT INTO reservation (id, book_id, member_id, reserved_at, status) VALUES (1, 3, 2, '2025-12-15T10:00:00', 'PENDING');
