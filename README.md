# user-management-api-springboot
user-role-status-management-api

# Spring Boot CRUD REST API with MySQL

A Spring Boot-based RESTful web service for managing **Users**, **Roles**, and **Statuses**. This project demonstrates how to build a CRUD API with clean architecture, using **Spring Boot**, **Spring Data JPA**, and **MySQL** as the backend.

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Postman (for API testing)
- Swagger (optional)

---

## 📦 Features

- Create, Read, Update, and Delete operations for:
  - Users
  - Roles
  - Statuses
- Relational mapping between entities (e.g., users have roles and statuses)
- Exception handling
- RESTful design principles
- Environment-based configuration

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/springboot-crud-rest-api.git
cd springboot-crud-rest-api



2. Configure MySQL
Create a database (e.g., user_crud_db) in your MySQL instance.

3. Update application.properties
properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/user_crud_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
4. Build and Run
bash
Copy
Edit
./mvnw spring-boot:run
Or build a WAR/JAR and run:

bash
Copy
Edit
mvn clean package
java -jar target/your-app-name.jar



Note:This is a sample CRUD project created for educational and demonstration purposes based on real backend development experience.