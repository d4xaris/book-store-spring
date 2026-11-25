# book-store-spring

Spring Boot and Thymeleaf web application for browsing books, managing a cart and orders, and handling role-based access with English and Ukrainian support.

## Features
- Thymeleaf UI with Bootstrap 5 and `en` / `uk` localization
- Book catalog with search, filtering, sorting, and pagination
- Cart and order lifecycle management for customers, employees, and admins
- Spring Security with BCrypt, role-based access, and JWT support
- Global error handling, AOP logging, and `test` / `prod` profiles

## Run
Set required environment variables (`DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`) for `prod`, or run with the `test` profile: