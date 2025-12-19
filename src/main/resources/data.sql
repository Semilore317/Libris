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

-- Create Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_LIBRARIAN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MEMBER');

-- Create Users
-- All passwords are "password" -> $2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u

-- Librarians
INSERT INTO users (id, username, password) VALUES (1, 'sarah.connor', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');
INSERT INTO users (id, username, password) VALUES (2, 'gandalf.grey', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');

-- Members
INSERT INTO users (id, username, password) VALUES (10, 'frodo.baggins', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');
INSERT INTO users (id, username, password) VALUES (11, 'hermione.granger', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');
INSERT INTO users (id, username, password) VALUES (12, 'tony.stark', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u');

-- Link Users to Roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- Sarah -> Librarian
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1); -- Gandalf -> Librarian
INSERT INTO user_roles (user_id, role_id) VALUES (10, 2); -- Frodo -> Member
INSERT INTO user_roles (user_id, role_id) VALUES (11, 2); -- Hermione -> Member
INSERT INTO user_roles (user_id, role_id) VALUES (12, 2); -- Tony -> Member

-- Create Members (Profiles)
INSERT INTO member (id, first_name, last_name, email, membership_number, user_id, joined_at) 
VALUES (1, 'Frodo', 'Baggins', 'frodo@shire.com', 'MEM-001', 10, '2024-01-10');

INSERT INTO member (id, first_name, last_name, email, membership_number, user_id, joined_at) 
VALUES (2, 'Hermione', 'Granger', 'hermione@hogwarts.edu', 'MEM-002', 11, '2024-02-15');

INSERT INTO member (id, first_name, last_name, email, membership_number, user_id, joined_at) 
VALUES (3, 'Tony', 'Stark', 'tony@starkindustries.com', 'MEM-003', 12, '2024-03-20');

-- Create Books
INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (1, 'The Fellowship of the Ring', 'J.R.R. Tolkien', '978-0547928210', 1954, 'FANTASY');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (2, 'Clean Code', 'Robert C. Martin', '978-0132350884', 2008, 'NON_FICTION');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (3, 'Dune', 'Frank Herbert', '978-0441013593', 1965, 'SCIENCE_FICTION');

-- Create Book Instances
-- Fellowship (Book 1): 2 copies
INSERT INTO book_instance (id, book_id, status) VALUES (1, 1, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (2, 1, 'ON_LOAN');

-- Clean Code (Book 2): 1 copy
INSERT INTO book_instance (id, book_id, status) VALUES (3, 2, 'AVAILABLE');

-- Dune (Book 3): 3 copies
INSERT INTO book_instance (id, book_id, status) VALUES (4, 3, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (5, 3, 'ON_LOAN');
INSERT INTO book_instance (id, book_id, status) VALUES (6, 3, 'AVAILABLE');

-- Create Loans
-- Frodo borrowed Book 1 Instance 2
INSERT INTO loan (id, book_instance_id, member_id, borrowed_at, due_date) 
VALUES (1, 2, 1, '2025-12-01T10:00:00', '2025-12-31T23:59:59');

-- Hermione borrowed Book 3 Instance 5
INSERT INTO loan (id, book_instance_id, member_id, borrowed_at, due_date) 
VALUES (2, 5, 2, '2025-12-10T14:30:00', '2026-01-10T23:59:59');

-- Reservations
INSERT INTO reservation (id, book_id, member_id, reserved_at, status) 
VALUES (1, 2, 3, '2025-12-15T09:00:00', 'PENDING'); -- Tony reserved Clean Code
