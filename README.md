# Libris

Libris is a full-stack Library Management System built with Spring Boot and Angular. It provides a RESTful API and a modern web interface for managing library operations including book inventory, member management, and lending workflows.

## Program Logic

The application is designed around the following core domains:

*   **Authentication & Authorization**: Stateless JWT-based auth with Role-Based Access Control (RBAC). Two roles: `ROLE_LIBRARIAN` and `ROLE_MEMBER`.
*   **User Management**:
    *   **Users**: Authentication credentials + role assignment.
    *   **Members**: Extended profile linked to a User `(One-to-One)` — separates auth data from library-specific member data.
*   **Inventory System**:
    *   **Book**: A bibliographic entry (Title, Author, ISBN, Genre, Publication Year, Cover Image URL).
    *   **BookInstance**: A physical copy of a book. Tracks individual copy status (`AVAILABLE`, `ON_LOAN`).
*   **Lending Operations**:
    *   **Loan**: Links a `Member` to a `BookInstance`. Tracks borrow date, due date, return date, and derived status (`ACTIVE`, `OVERDUE`, `RETURNED`).
    *   **Reservation**: Allows a `Member` to place a hold on a `Book` when all copies are on loan.

---

## Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| **Backend** | Spring Boot 3.x (Java 21) | REST API, business logic, security |
| **Backend** | Spring Security + JJWT | Stateless JWT auth, RBAC |
| **Backend** | Spring Data JPA + Hibernate | ORM, repository layer |
| **Backend** | PostgreSQL 16 | Primary relational database |
| **Backend** | Lombok | Reduces Java boilerplate |
| **Frontend** | Angular 19 (standalone) | SPA with signals-based reactivity |
| **Frontend** | TailwindCSS | Utility-first styling |
| **Frontend** | RxJS | Reactive HTTP client streams |
| **Infra** | Docker + Docker Compose | Containerised local development |
| **Storage** | Supabase Storage *(planned)* | Book cover image hosting |

---

## Getting Started

### With Docker (Recommended)

> **Prerequisites:** Docker + Docker Compose

```bash
git clone https://github.com/youruser/libris.git
cd libris/infra
docker compose up --build
```

| Service | URL |
|---|---|
| API | http://localhost:8080/api/v1 |
| Client | http://localhost:4200 |

---

### Manual Setup

> **Prerequisites:** Java 21, Node 20+, PostgreSQL 16

**1. Database**

```sql
CREATE DATABASE libris_db;
CREATE USER libris_dev WITH PASSWORD 'shhhh';
GRANT ALL PRIVILEGES ON DATABASE libris_db TO libris_dev;
```

**2. Backend**

```bash
cd server
chmod +x mvnw
./mvnw spring-boot:run
```

API available at `http://localhost:8080/api/v1`

**3. Frontend**

```bash
cd client
npm install
npm run dev       # or: ng serve
```

Client available at `http://localhost:4200`

---

### Default Accounts

All accounts use password: **`password`**

| Role | Username | Notes |
|---|---|---|
| Librarian | `sarah.connor` | Full admin access |
| Librarian | `gandalf.grey` | Full admin access |
| Member | `frodo.baggins` | MEM-001 |
| Member | `hermione.granger` | MEM-002 |
| Member | `tony.stark` | MEM-003 |

---

## API Documentation

A Postman collection is included for exploring all endpoints.

*   **[Postman Collection](./postman_collection.json)** — import into Postman to test all available endpoints with example requests.
