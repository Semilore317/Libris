# Libris

Libris is a Library Management System built with Java and Spring Boot. It provides a RESTful API for managing library operations, including book inventory, user management, and lending processes.

## Program Logic

The application is designed around the following:

*   **Authentication & Authorization**: The system uses **JWT (JSON Web Tokens)** for secure stateless authentication. Access is controlled via Role-Based Access Control (RBAC) with predefined roles such as `LIBRARIAN` and `MEMBER`.
*   **User Management**:
    *   **Users**: Detailed login credentials and role assignment.
    *   **Members**: Extended profile information linked to Users `(One-to-One)`, allowing library-specific data (like membership status) to be kept separate from authentication data.
*   **Inventory System**:
    *   **Book**: Represents a bibliography entry (Title, Author, ISBN, etc.).
    *   **BookInstance**: Represents a physical copy of a book. This distinction allows the library to track multiple copies of the same title independently (e.g., for availability or lost status).
*   **Lending Operations**:
    *   **Loan**: Connects a `Member` to a `BookInstance`. It tracks the issue date, due date, and return date.
    *   **Reservations**: Allows a `Member` to reserve a `Book` when all instances are currently borrowed.

## Prerequisites

Before running the application, ensure you have the following installed:

*   **Java 21 JDK**
*   **PostgreSQL** (running locally or accessible via network)

## Setup & Running

### 1. Database Configuration

The application is configured to connect to a PostgreSQL database. By default, it expects the following configuration (as defined in `src/main/resources/application.properties`):

*   **Host**: `localhost`
*   **Port**: `5432`
*   **Database**: `libris_db`
*   **Username**: `libris_dev`
*   **Password**: `shhhh`

You must create this database and user before starting the application:

```sql
CREATE DATABASE libris_db;
CREATE USER libris_dev WITH PASSWORD 'shhhh';
GRANT ALL PRIVILEGES ON DATABASE libris_db TO libris_dev;
```

### 2. Running the Application

Use the included Maven Wrapper to run the application directly from the command line:

```bash
# Ensure the wrapper is executable
chmod +x mvnw

# Run the application
./mvnw spring-boot:run
```

The application will start on port `8080`.

### 3. Application Access

Once running, the API is accessible at:

```
http://localhost:8080/api/v1
```

## API Documentation

Postman documentation is available to help you test and explore the API.

*   **[Postman Collection](./postman_collection.json)**: You can import this JSON file into Postman to see all available endpoints and example requests.

