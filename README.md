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

# API Endpoints Documentation

## Event Simulation and Management

### Simulate Event by Item ID
- **URL**: `/events/{name}/Sim/{id}`
- **Method**: `GET`
- **Description**: Simulate an event for a specific event item by its ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the event item.

### Simulate Event
- **URL**: `/events/{name}/Sim`
- **Method**: `GET`
- **Description**: Simulate an event for all event items associated with the specified event.
- **Path Variable**:
  - `name`: Name of the event.

### Generate Records
- **URL**: `/events/{name}/Gen`
- **Method**: `GET`
- **Description**: Generate sample records for a specified event.
- **Path Variable**:
  - `name`: Name of the event.

### Top Nations
- **URL**: `/events/TopN/{category}`
- **Method**: `GET`
- **Description**: Retrieve the top nation based on the specified category across all events.
- **Path Variable**:
  - `category`: Category for sorting (1: Gold, 2: Silver, 3: Bronze, 4: Points).

### Top Nations by Event
- **URL**: `/events/{name}/TopN/{category}`
- **Method**: `GET`
- **Description**: Retrieve the top nation for a specific event based on the specified category.
- **Path Variables**:
  - `name`: Name of the event.
  - `category`: Category for sorting (1: Gold, 2: Silver, 3: Bronze, 4: Points).

### Lowest Nations
- **URL**: `/events/LowN/{category}`
- **Method**: `GET`
- **Description**: Retrieve the lowest nation based on the specified category across all events.
- **Path Variable**:
  - `category`: Category for sorting (1: Gold, 2: Silver, 3: Bronze, 4: Points).

### Lowest Nations by Event
- **URL**: `/events/{name}/LowN/{category}`
- **Method**: `GET`
- **Description**: Retrieve the lowest nation for a specific event based on the specified category.
- **Path Variables**:
  - `name`: Name of the event.
  - `category`: Category for sorting (1: Gold, 2: Silver, 3: Bronze, 4: Points).

### Highest Medal Athlete
- **URL**: `/events/HMA`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest number of medals across all events.

### Highest Medal Athlete by Event
- **URL**: `/events/{name}/HMA`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest number of medals for a specific event.
- **Path Variable**:
  - `name`: Name of the event.

### Highest Points Athlete
- **URL**: `/events/HPA`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest points across all events.

### Highest Points Athlete by Gender
- **URL**: `/events/HPA/{gender}`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest points by gender.
- **Path Variable**:
  - `gender`: Gender of the athlete (1: Female, 2: Male).

### Highest Points Athlete by Event
- **URL**: `/events/{name}/HPA`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest points for a specific event.
- **Path Variable**:
  - `name`: Name of the event.

### Highest Points Athlete by Event and Gender
- **URL**: `/events/{name}/HPA/{gender}`
- **Method**: `GET`
- **Description**: Retrieve the athlete with the highest points for a specific event and gender.
- **Path Variables**:
  - `name`: Name of the event.
  - `gender`: Gender of the athlete (1: Female, 2: Male).

### Medal Tally
- **URL**: `/events/MT{n}`
- **Method**: `GET`
- **Description**: Retrieve the top `n` medal tallies across all events.
- **Path Variable**:
  - `n`: Number of top medal tallies to retrieve.

### Medal Tally by Event
- **URL**: `/events/{name}/MT{n}`
- **Method**: `GET`
- **Description**: Retrieve the top `n` medal tallies for a specific event.
- **Path Variables**:
  - `name`: Name of the event.
  - `n`: Number of top medal tallies to retrieve.

## Event Management

### Get All Events
- **URL**: `/events/ALlEvents`
- **Method**: `GET`
- **Description**: Retrieve a list of all events.

### Get Event by Name
- **URL**: `/events/ALlEvents/{name}`
- **Method**: `GET`
- **Description**: Retrieve a specific event by its name.
- **Path Variable**:
  - `name`: Name of the event.

### Create Event
- **URL**: `/events/ALlEvents/addNew`
- **Method**: `POST`
- **Description**: Create a new event.
- **Request Body**: JSON object containing the event name.

### Update Event
- **URL**: `/events/ALlEvents/{id}`
- **Method**: `PUT`
- **Description**: Update the name of an existing event.
- **Path Variable**:
  - `id`: ID of the event.
- **Request Body**: JSON object containing the new event name.

### Delete Event
- **URL**: `/events/ALlEvents/{id}`
- **Method**: `DELETE`
- **Description**: Delete an event by its ID.
- **Path Variable**:
  - `id`: ID of the event.

## Country Management

### Get All Countries
- **URL**: `/events/{name}/countries`
- **Method**: `GET`
- **Description**: Retrieve a list of all countries associated with a specific event.
- **Path Variable**:
  - `name`: Name of the event.

### Get Country by ID
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `GET`
- **Description**: Retrieve a specific country by its ISO code for a specific event.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ISO code of the country.

### Create Country
- **URL**: `/events/{name}/countries`
- **Method**: `POST`
- **Description**: Create a new country.
- **Path Variable**:
  - `name`: Name of the event.
- **Request Body**: JSON object containing country details.

### Update Country
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `PUT`
- **Description**: Update the details of a country.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ISO code of the country.
- **Request Body**: JSON object containing updated country details.

### Delete Country
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `DELETE`
- **Description**: Delete a country by its ISO code.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ISO code of the country.

## Event Item Management

### Get All Event Items
- **URL**: `/events/{name}/eventItems`
- **Method**: `GET`
- **Description**: Retrieve a list of all event items associated with a specific event.
- **Path Variable**:
  - `name`: Name of the event.

### Get Event Item by ID
- **URL**: `/events/{name}/eventItems/{id}`
- **Method**: `GET`
- **Description**: Retrieve a specific event item by its ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the event item.

### Get Winners by Event Item ID
- **URL**: `/events/{name}/eventItems/{id}/Winners`
- **Method**: `GET`
- **Description**: Retrieve the winners (gold, silver, bronze) for a specific event item.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the event item.

### Create Event Item
- **URL**: `/events/{name}/eventItems`
- **Method**: `POST`
- **Description**: Create a new event item for a specific event.
- **Path Variable**:
  - `name`: Name of the event.
- **Request Body**: JSON object containing event item details.

### Delete Event Item
- **URL**: `/events/{name}/eventItems/{id}`
- **Method**: `DELETE`
- **Description**: Delete an event item by its ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the event item.

## Athlete Management

### Get All Athletes
- **URL**: `/events/{name}/athletes`
- **Method**: `GET`
- **Description**: Retrieve a list of all athletes participating in a specific event.
- **Path Variable**:
  - `name`: Name of the event.

### Get Athlete by ID
- **URL**: `/events/{name}/athletes/{id}`
- **Method**: `GET`
- **Description**: Retrieve a specific athlete by their ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the athlete.

### Create Athlete
- **URL**: `/events/{name}/athletes`
- **Method**: `POST`
- **Description**: Create a new athlete for a specific event.
- **Path Variable**:
  - `name`: Name of the event.
- **Request Body**: JSON object containing athlete details.

### Delete Athlete
- **URL**: `/events/{name}/athletes/{id}`
- **Method**: `DELETE`
- **Description**: Delete an athlete by their ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the athlete.

## Additional Endpoints

### Get Medal Tally by Country and Event
- **URL**: `/events/{name}/countries/{id}/tally`
- **Method**: `GET`
- **Description**: Retrieve the medal tally for a specific country and event.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ISO code of the country.

### Get Event Item Records
- **URL**: `/events/{name}/eventItems/{id}/records`
- **Method**: `GET`
- **Description**: Retrieve records of an event item by its ID.
- **Path Variables**:
  - `name`: Name of the event.
  - `id`: ID of the event item.


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

