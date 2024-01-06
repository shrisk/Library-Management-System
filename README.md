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
Download Community Edition from : https://www.sonarsource.com/products/sonarqube/downloads/

Extract the SonarQube ZIP File:
Extract the downloaded SonarQube ZIP file to a location on your machine.

Start SonarQube Server:
Navigate to the SonarQube directory in the terminal or command prompt.

Run the following command to start the SonarQube server:
bin\windows-x86-64\StartSonar.bat  # For Windows

Wait for the server to start. You can check the logs in the console for any errors.

Access SonarQube Web Interface:

Open your web browser and go to http://localhost:9000.
The default login credentials are:
Username: admin
Password: admin

Generate and add the secret key in pom.xml

## JDK 17
Install JDK 17 version to support sonarQube scanner.
https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.zip

