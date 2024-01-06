# Library Management System

A simple Spring Boot application for a Library Management System.

## Introduction

This project is a basic implementation of a Library Management System using Spring Boot. It provides RESTful APIs for managing books in an in-memory storage.

## Technologies

- Java 11
- Spring Boot 2.6.3
- Maven (for building and managing dependencies)

## Build the project:
mvn clean install

## Run the application:

mvn spring-boot:run
The application will be available at http://localhost:8080.

## Endpoints
Add Book:

POST /library/addBook
Get All Books:


GET /library/getAllBooks
Get Book by ID:


GET /library/getBook/id/{id}
Get Book by Book Title:


GET /library/getBook/title/{title}
Update Book:


PUT /library/updateBook/{id}
Delete Book:

DELETE /library/deleteBook/{id}

## Usage
Use tools like Postman to interact with the APIs.
Customize the provided Postman collection for testing.


## SonarQube Scanner
generate and add the secret key in pom.xml

