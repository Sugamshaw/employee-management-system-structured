
# Employee Management System

A full-stack web application for managing employee records with CRUD operations, built with Spring Boot, MySQL, and a responsive HTML/CSS/JavaScript frontend.

## Tech Stack
- **Backend:** Spring Boot 2.7.x, Spring Web, Spring Data JPA, Bean Validation
- **Database:** MySQL 8
- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Build:** Maven

## Quickstart
```bash
# 1) Configure DB (create if needed)
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS employee_db"
# 2) Edit credentials in src/main/resources/application.properties
# 3) Run the app
mvn spring-boot:run
```
Open http://localhost:8080 • API at `/api/employees`

Full-stack Employee Management System built with Spring Boot, RESTful APIs, MySQL, JavaScript, HTML, and CSS.  
Implements CRUD operations with a responsive frontend and secure, scalable backend integration.
