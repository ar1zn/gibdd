## README

### Project Description

This application is a REST service for generating vehicle license plate numbers, implemented in Java 17 using Spring Boot. The application runs on an Apache Tomcat 8 or higher application server.

### Requirements

- Java 8 or higher
- Apache Tomcat 8 or higher
- Build automation system: Maven
- Database: PostgreSQL

### API

Application context: `/number`

The application provides two GET methods:

1. **random**

   Generates a random license plate number in the format `A111AA 116 RUS`, where `A` is any character from the list [А, Е, Т, О, Р, Н, У, К, Х, С, В, М], `1` is any digit, and `116 RUS` is a constant. The number must not duplicate any previously issued number.

   **Example request:**
   ```http
   GET http://localhost:8080/number/random
   ```

   **Example response:**
   ```
   C399BA 116 RUS
   ```

2. **next**

   Generates the next sequential license plate number starting from the smallest. The number must not duplicate any previously issued number.

   **Example request:**
   ```http
   GET http://localhost:8080/number/next
   ```

   **Example response:**
   ```
   C400BA 116 RUS
   ```
### Architecture

The application follows the Model-View-Controller (MVC) architectural pattern. 

- **Model**: Represents the data and business logic of the application. In this project, the number generation logic is encapsulated within the service layer (`NumberService.java`).
- **View**: As this is a REST service, the view is represented by the API responses.
- **Controller**: Manages the user input and calls the model to retrieve data. This is implemented in `NumberController.java`, which handles the API requests and invokes the appropriate service methods to generate the license plate numbers.
