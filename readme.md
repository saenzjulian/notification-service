# Rate-Limited Notification Service

## Overview

The **Rate-Limited Notification Service** is a backend application designed to manage notifications with rate limits to protect recipients from receiving too many messages. This service is built with Java, Spring Boot, and PostgreSQL, leveraging Flyway for database migrations. It demonstrates good software practices, including clean architecture, exception handling, and rate-limiting logic.

### Key Features

- **Rate Limiting**: Controls the number of notifications sent per recipient per type (e.g., status, news, marketing).
- **Scalable and Extendable**: Built with clean code principles, using design patterns that allow for easy extensions.
- **Database Integration**: Uses PostgreSQL as the database and Flyway for schema migrations.
- **Dockerized Deployment**: Easily deployable using Docker and Docker Compose.
- **API Documentation**: Swagger integration for easy API exploration.

## Table of Contents

1. [Tech Stack](#tech-stack)
2. [Architecture](#architecture)
3. [Setup Instructions](#setup-instructions)
4. [Usage](#usage)

## Tech Stack

- **Java 21**: The programming language used for the application.
- **Spring Boot**: For building the backend service.
- **PostgreSQL**: The relational database management system.
- **Flyway**: For database migrations and version control.
- **Docker & Docker Compose**: For containerization and easy deployment.
- **Lombok**: To reduce boilerplate code.
- **Swagger/OpenAPI**: For API documentation.
- **JUnit**: For testing.

## Architecture

The service is built following the principles of **Clean Architecture**, ensuring that the core business logic is decoupled from external dependencies such as databases and frameworks. Key components include:

- **Domain Layer**: Contains core business logic and domain entities.
- **Application Layer**: Services and rate-limiting logic.
- **Infrastructure Layer**: Database repositories, controllers, and external interfaces.
- **Presentation Layer**: REST controllers and response handling.

### Design Patterns

- **Repository Pattern**: For data access abstraction.
- **Strategy Pattern**: For handling rate-limiting logic based on notification type.

## Setup Instructions

### Prerequisites

- **Docker** and **Docker Compose** installed on your machine.

### Installation

1. **Clone the repository**:

    ```bash
    git clone https://github.com/saenzjulian/notification-service.git
    cd notification-service
    ```

2. **Run the Application with Docker Compose**:

   The application is fully containerized, including the database. Use Docker Compose to start all services:

    ```bash
    docker-compose up -d --build
    ```

   This command will:

   - Build the application Docker image.
   - Start the Spring Boot application and PostgreSQL database.
   - Run database migrations using Flyway automatically.

3. **Access Swagger API Documentation**:

   Once the application is running, navigate to [http://localhost:8081/swagger-ui.html](http://localhost:8080/swagger-ui.html) to explore and test the API endpoints. Swagger provides a user-friendly interface to view API documentation, try out requests, and see responses.

## Usage

### Sending Notifications

To send a notification, use the `POST /api/v1/notification` endpoint with the following parameters:

- `type` (e.g., `status`, `news`, `marketing`)
- `username` (recipient's username)
- `message` (notification message)

Example Request:

```bash
curl -X POST "http://localhost:8080/api/v1/notification" \
     -H "Content-Type: application/json" \
     -d '{
           "type": "status",
           "username": "user123",
           "message": "This is a status update."
         }'