# Event Management System

## Overview

The Event Management System is a Java-based application that facilitates the management of events(eg.Olympics), including generating data, tracking athlete performance, and calculating medal tallies. This system leverages Spring Boot, Hibernate (JPA), and various other technologies to provide a comprehensive solution for managing and analyzing Olympic events.

## Features
- **REST API** for managing Olympic events, athletes, and medal tallies.
- **Data Simulation** for creating mock data of events and athletes.
- **Medal Tally Calculation** by country and event.
- **Database Integration** with support for Hibernate ORM.
  
## Technologies Used

- **Java**: Programming language used for developing the application.
- **Spring Boot**: Framework used for building and deploying the application.
- **Hibernate (JPA)**: ORM framework used for database interactions.
- **Spring Data JPA**: Provides data access layers with repositories.
- **Java Faker**: Library used for generating sample data.
- **Postman** for API testing

## Setup and Configuration

### Prerequisites

- Java 11 or higher
- Maven
- IntelliJ IDEA (or another Java IDE)
- PostgreSQL (or any other database supported by Hibernate)
- Postman For testing the API endpoints, download Postman [here](https://www.postman.com/downloads/).
  
### Installation

1. **Clone the Repository**

     ```bash
    git clone https://github.com/DKS2301/HartreeBackendProject.git
    ```
    
2. **Configure Database**

   Open the `src/main/resources/application.properties` file and configure your database connection:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build the Project**

    Run the following Maven command to build the project:

    ```bash
    mvn clean install
    ```

4. **Run the Application**

    You can run the application from IntelliJ IDEA or using the Maven command:

    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

### Event Simulation

- **`GET /functions/Sim/{id}`**: Simulates the event with the given `id`.

### Data Generation

- **`GET /functions/Gen/{records}`**: Generates a specified number of records.

### Medal Tallies

- **`GET /functions/TopN/{category}`**: Retrieves the country with the highest points for the given category (1: gold, 2: silver, 3: bronze, 4: total points).
- **`GET /functions/LowN/{category}`**: Retrieves the country with the lowest points for the given category.

### Athlete Performance

- **`GET /functions/HMA`**: Retrieves the athlete with the highest number of medals across all events.
- **`GET /functions/HMA/{eventId}`**: Retrieves the athlete with the highest number of medals for a specific event.
- **`GET /functions/HPA`**: Retrieves the athlete with the maximum points across all events.
- **`GET /functions/HPA/{gender}`**: Retrieves the athlete with the maximum points filtered by gender (1: female, 2: male).

### Medal Tally by Country

- **`GET /functions/MT{n}`**: Retrieves the top `n` countries based on medal tally.
- **`GET /functions/MT{n}/{eventId}`**: Retrieves the top `n` countries for a specific event based on medal tally.

### Testing
Use **Postman** to test the APIs:
1. Open Postman and create a new request.
2. Select the appropriate HTTP method (GET, POST, etc.).
3. Enter the API URL (e.g., `http://localhost:8080/Sim/1`).
4. Send the request and inspect the response.


## Example Usage

- **Simulate an Event**: `GET http://localhost:8080/functions/Sim/1`
- **Generate 100 Records**: `GET http://localhost:8080/functions/Gen/100`
- **Get Top Country by Gold Medals**: `GET http://localhost:8080/functions/TopN/1`


## Contributing

Feel free to submit issues and pull requests. Contributions are welcome!

### License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact
For any inquiries or issues, please contact [DKS2301](https://github.com/DKS2301).

