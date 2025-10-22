# Springboot REST API CRUD App
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/panoschron97/Springboot_Rest_Api_Crud_App)

This project is a comprehensive RESTful API built with Spring Boot. It demonstrates full CRUD (Create, Read, Update, Delete) functionality for a company management system, including entities for Companies, Departments, Tasks, and Employee Information. The application is secured using Spring Security with role-based access control and integrates Springdoc OpenAPI for interactive API documentation.

## Features

- **RESTful API:** Provides CRUD endpoints for managing companies, departments, tasks, and employees.
- **Database Schema:** Includes a detailed MySQL database setup with tables, relationships, constraints, triggers, and stored procedures.
- **Spring Security:** Implements role-based access control (RBAC) for different API endpoints using JDBC-based authentication.
- **JPA & Hibernate:** Uses Spring Data JPA for data persistence and object-relational mapping.
- **API Documentation:** Automatically generates interactive API documentation with Swagger UI via Springdoc.
- **Custom Exception Handling:** Provides clear, structured error responses for API requests.

## Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 25 or later
- Apache Maven
- MySQL Server

## Getting Started

Follow these steps to get the application up and running on your local machine.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/panoschron97/Springboot_Rest_Api_Crud_App.git
    cd Springboot_Rest_Api_Crud_App
    ```

2.  **Database Setup:**
    -   Log in to your MySQL server and create a database named `application`.
        ```sql
        CREATE DATABASE IF NOT EXISTS application;
        ```
    -   Run the provided SQL scripts to set up the schema and seed the data. Execute the files in the following order:
        1.  `Application.sql` (Creates the main tables, triggers, and procedures)
        2.  `security.sql` (Creates user and authority tables for security)

3.  **Configure Application Properties:**
    -   Navigate to `restapi/src/main/resources/application.properties`.
    -   Update the database connection details to match your MySQL setup:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/application
        spring.datasource.username=your_mysql_username
        spring.datasource.password=your_mysql_password
        ```

4.  **Build and Run the Application:**
    -   Navigate to the `restapi` directory.
    -   Run the application using the Maven wrapper.
        - On macOS/Linux:
          ```bash
          ./mvnw spring-boot:run
          ```
        - On Windows:
          ```bash
          ./mvnw.cmd spring-boot:run
          ```
    The application will start on `http://localhost:8080`.

## API Endpoints

The API provides CRUD endpoints for the following entities. All endpoints are relative to the base path `/api`.

### Companies
- `GET /api/companies`: Get a list of all companies.
- `GET /api/companies/{id}`: Get a company by its ID.
- `POST /api/companies`: Create a new company.
- `PUT /api/companies`: Update an existing company.
- `PATCH /api/companies/{id}`: Partially update a company.
- `DELETE /api/companies/{id}`: Delete a company by its ID.

### Departments
- `GET /api/departments`: Get a list of all departments.
- `GET /api/departments/{id}`: Get a department by its ID.
- `POST /api/departments`: Create a new department.
- `PUT /api/departments`: Update an existing department.
- `PATCH /api/departments/{id}`: Partially update a department.
- `DELETE /api/departments/{id}`: Delete a department by its ID.

### Tasks
- `GET /api/tasks`: Get a list of all tasks.
- `GET /api/tasks/{id}`: Get a task by its ID.
- `POST /api/tasks`: Create a new task.
- `PUT /api/tasks`: Update an existing task.
- `PATCH /api/tasks/{id}`: Partially update a task.
- `DELETE /api/tasks/{id}`: Delete a task by its ID.

### Information (Employees)
- `GET /api/information`: Get a list of all employees.
- `GET /api/information/{id}`: Get an employee by their ID.
- `POST /api/information`: Create a new employee record.
- `PUT /api/information`: Update an existing employee record.
- `PATCH /api/information/{id}`: Partially update an employee record.
- `DELETE /api/information/{id}`: Delete an employee record by its ID.

## Security

The API is secured using Basic Authentication and a role-based access system. The default users and roles are defined in `security.sql`. The password for all default users is `password`.

**User Roles and Permissions:**
-   **ROLE_EMPLOYEE**: Can perform `GET` requests (read-only access).
-   **ROLE_MANAGER**: Can perform `GET`, `POST`, `PUT`, and `PATCH` requests (read, create, update).
-   **ROLE_ADMIN**: Can perform all operations, including `DELETE`.

**Default Users:**
| Username | Password  | Roles                         |
| :------- | :-------- | :---------------------------- |
| `alexis` | `password`| `ROLE_EMPLOYEE`               |
| `nikos`  | `password`| `ROLE_EMPLOYEE`, `ROLE_MANAGER` |
| `panos`  | `password`| `ROLE_EMPLOYEE`, `ROLE_MANAGER`, `ROLE_ADMIN` |

## API Documentation (Swagger)

Once the application is running, you can access the interactive Swagger UI to explore and test the API endpoints.

-   **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
-   **OpenAPI Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Technologies Used

-   **Backend:** Spring Boot, Spring Web, Spring Data JPA, Spring Security
-   **Database:** MySQL
-   **Build Tool:** Apache Maven
-   **API Documentation:** Springdoc OpenAPI (Swagger)
