# University Management

is a backend service designed to handle core academic entities, including Universities, Faculties, Departments, Featured Programs, Users, and Audit Logs.
Each University can manage multiple Faculties, and each Faculty oversees its own Departments.
Featured Programs are associated with specific Universities.
Users represent authenticated accounts, and every action is securely recorded in Audit Logs for transparency and traceability.

## Prerequisites

Make sure you have installed:

- Java JDK 17+ with Spring Boot, Maven, Hibernate JPA 
- MySQL as database
- Postman or any API client for testing  

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/mayawuland/university-management-backend.git
cd university-management-backend
```

2. Build the project using Maven:

```bash
mvn clean install
```

3. Configure your database connection in application.properties:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/universitymanagement_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

## Running the Project

Run the Spring Boot application using Maven:
```bash
mvn spring-boot:run
```
## Testing & API Documentation

You can test the API using Postman or any other API testing tool. The full API documentation is available [here](https://drive.google.com/file/d/17UWWhJ9c9ChJKzQ9VFO54AGsYi2JG1NI/view?usp=drive_link).

