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
INSERT INTO users (id, username, password, user_type) VALUES (1, 'sarah.connor', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u', 'LIBRARIAN');
INSERT INTO users (id, username, password, user_type) VALUES (2, 'gandalf.grey', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u', 'LIBRARIAN');

-- Members
INSERT INTO users (id, username, password, user_type) VALUES (10, 'frodo.baggins', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u', 'MEMBER');
INSERT INTO users (id, username, password, user_type) VALUES (11, 'hermione.granger', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u', 'MEMBER');
INSERT INTO users (id, username, password, user_type) VALUES (12, 'tony.stark', '$2a$10$y69xckW3NmcINguy31s.k.v4TXNsn4eLCsRmnewo17xMvIouQ/Q7u', 'MEMBER');

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

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (4, 'Project Hail Mary', 'Andy Weir', '978-0593135204', 2021, 'SCIENCE_FICTION');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (5, 'The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 1925, 'CLASSIC');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (6, 'The Pragmatic Programmer', 'Andrew Hunt', '978-0135957059', 1999, 'NON_FICTION');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (7, 'Foundation', 'Isaac Asimov', '978-0553293357', 1951, 'SCIENCE_FICTION');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (8, 'Atomic Habits', 'James Clear', '978-0735211292', 2018, 'SELF_HELP');

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

-- Project Hail Mary (Book 4): 2 copies
INSERT INTO book_instance (id, book_id, status) VALUES (7, 4, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (8, 4, 'AVAILABLE');

-- Great Gatsby (Book 5): 1 copy
INSERT INTO book_instance (id, book_id, status) VALUES (9, 5, 'AVAILABLE');

-- Pragmatic Programmer (Book 6): 2 copies
INSERT INTO book_instance (id, book_id, status) VALUES (10, 6, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (11, 6, 'AVAILABLE');

-- Foundation (Book 7): 3 copies
INSERT INTO book_instance (id, book_id, status) VALUES (12, 7, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (13, 7, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (14, 7, 'AVAILABLE');

-- Atomic Habits (Book 8): 2 copies
INSERT INTO book_instance (id, book_id, status) VALUES (15, 8, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (16, 8, 'AVAILABLE');

-- New Books
INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (9, 'The Hobbit', 'J.R.R. Tolkien', '978-0547928227', 1937, 'FANTASY');
INSERT INTO book_instance (id, book_id, status) VALUES (17, 9, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (18, 9, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (10, '1984', 'George Orwell', '978-0451524935', 1949, 'CLASSIC');
INSERT INTO book_instance (id, book_id, status) VALUES (19, 10, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (11, 'The Martian', 'Andy Weir', '978-0553418026', 2011, 'SCIENCE_FICTION');
INSERT INTO book_instance (id, book_id, status) VALUES (20, 11, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (21, 11, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (12, 'Effective Java', 'Joshua Bloch', '978-0134685991', 2017, 'NON_FICTION');
INSERT INTO book_instance (id, book_id, status) VALUES (22, 12, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (13, 'Harry Potter and the Sorcerers Stone', 'J.K. Rowling', '978-0590353427', 1997, 'FANTASY');
INSERT INTO book_instance (id, book_id, status) VALUES (23, 13, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (24, 13, 'AVAILABLE');
INSERT INTO book_instance (id, book_id, status) VALUES (25, 13, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (14, 'To Kill a Mockingbird', 'Harper Lee', '978-0060935467', 1960, 'CLASSIC');
INSERT INTO book_instance (id, book_id, status) VALUES (26, 14, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (15, 'Steve Jobs', 'Walter Isaacson', '978-1451648538', 2011, 'NON_FICTION');
INSERT INTO book_instance (id, book_id, status) VALUES (27, 15, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (16, 'The Silent Patient', 'Alex Michaelides', '978-1250301697', 2019, 'THRILLER');
INSERT INTO book_instance (id, book_id, status) VALUES (28, 16, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (17, 'Normal People', 'Sally Rooney', '978-1984822178', 2018, 'FICTION');
INSERT INTO book_instance (id, book_id, status) VALUES (29, 17, 'AVAILABLE');

INSERT INTO book (id, title, author, isbn, publication_year, genre) 
VALUES (18, 'The Alchemist', 'Paulo Coelho', '978-0062315007', 1988, 'FICTION');
INSERT INTO book_instance (id, book_id, status) VALUES (30, 18, 'AVAILABLE');

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

-- Sync Sequences (Necessary because of manual ID inserts)
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));
SELECT setval('member_id_seq', (SELECT MAX(id) FROM member));
SELECT setval('book_id_seq', (SELECT MAX(id) FROM book));
SELECT setval('book_instance_id_seq', (SELECT MAX(id) FROM book_instance));
SELECT setval('loan_id_seq', (SELECT MAX(id) FROM loan));
SELECT setval('reservation_id_seq', (SELECT MAX(id) FROM reservation));
