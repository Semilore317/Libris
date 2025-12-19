# Libris

Libris is a Library Management System.

## Docker Setup

### Prerequisites
- Docker
- Docker Compose

### Running the Application

1.  Build and start the services:
    ```bash
    docker-compose up --build -d
    ```

2.  The application will be accessible at:
    - API: `http://localhost:8081/api/v1`

3.  The PostgreSQL database is accessible at `localhost:5433`.

### Stopping the Application

To stop the containers:
```bash
docker-compose down
```
