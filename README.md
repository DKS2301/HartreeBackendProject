# Event Management System

## Overview

The Event Management System is a Java-based application that facilitates the management of events (e.g., Olympics), including generating data, tracking athlete performance, and calculating medal tallies. This system leverages Spring Boot, Hibernate (JPA), and various other technologies to provide a comprehensive solution for managing and analyzing Olympic events.

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
- **Postman**: For API testing
- **Docker**: For containerizing the application
- **Docker Compose**: For defining and running multi-container Docker applications

## Setup and Configuration

### Prerequisites

- Java 11 or higher
- Maven
- Docker and Docker Compose
- Postman For testing the API endpoints, download Postman [here](https://www.postman.com/downloads/).

### Dockerizing the Application

1. **Clone the Repository**

    ```bash
    git clone https://github.com/DKS2301/HartreeBackendProject.git
    cd HartreeBackendProject
    ```

2. **Build and Run with Docker Compose**

    Run Docker Compose to build and start the application and database:

    ```bash
    docker-compose up --build
    ```

    This command will:
    - Build the Docker images for your Spring Boot application and PostgreSQL database.
    - Start the containers with the specified configuration.

3. **Accessing the Application**

    - **Spring Boot Application**: Access the application at `http://localhost:8080`.
    - **PostgreSQL Database**: Connect to the database at `localhost:5432` using the credentials `postgres`/`yourpassword` and database name `Eventsdb`.

4. **Stopping and Removing Containers**

    To stop and remove the containers, use:

    ```bash
    docker-compose down
    ```

## API Endpoints

### Event Simulation and Management

#### Simulate Event by Item ID
- **URL**: `/events/{name}/Sim/{id}`
- **Method**: `GET`

#### Simulate Event
- **URL**: `/events/{name}/Sim`
- **Method**: `GET`

#### Generate Records
- **URL**: `/events/{name}/Gen`
- **Method**: `GET`

#### Top Nations
- **URL**: `/events/TopN/{category}`
- **Method**: `GET`

#### Top Nations by Event
- **URL**: `/events/{name}/TopN/{category}`
- **Method**: `GET`

#### Lowest Nations
- **URL**: `/events/LowN/{category}`
- **Method**: `GET`

#### Lowest Nations by Event
- **URL**: `/events/{name}/LowN/{category}`
- **Method**: `GET`

#### Highest Medal Athlete
- **URL**: `/events/HMA`
- **Method**: `GET`

#### Highest Medal Athlete by Event
- **URL**: `/events/{name}/HMA`
- **Method**: `GET`

#### Highest Points Athlete
- **URL**: `/events/HPA`
- **Method**: `GET`

#### Highest Points Athlete by Gender
- **URL**: `/events/HPA/{gender}`
- **Method**: `GET`

#### Highest Points Athlete by Event
- **URL**: `/events/{name}/HPA`
- **Method**: `GET`

#### Highest Points Athlete by Event and Gender
- **URL**: `/events/{name}/HPA/{gender}`
- **Method**: `GET`

#### Medal Tally
- **URL**: `/events/MT{n}`
- **Method**: `GET`

#### Medal Tally by Event
- **URL**: `/events/{name}/MT{n}`
- **Method**: `GET`

### Event Management

#### Get All Events
- **URL**: `/events/AllEvents`
- **Method**: `GET`

#### Get Event by Name
- **URL**: `/events/AllEvents/{name}`
- **Method**: `GET`

#### Create Event
- **URL**: `/events/AllEvents/addNew`
- **Method**: `POST`

#### Update Event
- **URL**: `/events/AllEvents/{id}`
- **Method**: `PUT`

#### Delete Event
- **URL**: `/events/AllEvents/{id}`
- **Method**: `DELETE`

### Country Management

#### Get All Countries
- **URL**: `/events/{name}/countries`
- **Method**: `GET`

#### Get Country by ID
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `GET`

#### Create Country
- **URL**: `/events/{name}/countries`
- **Method**: `POST`

#### Update Country
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `PUT`

#### Delete Country
- **URL**: `/events/{name}/countries/{id}`
- **Method**: `DELETE`

### Event Item Management

#### Get All Event Items
- **URL**: `/events/{name}/eventItems`
- **Method**: `GET`

#### Get Event Item by ID
- **URL**: `/events/{name}/eventItems/{id}`
- **Method**: `GET`

#### Get Winners by Event Item ID
- **URL**: `/events/{name}/eventItems/{id}/Winners`
- **Method**: `GET`

#### Create Event Item
- **URL**: `/events/{name}/eventItems`
- **Method**: `POST`

#### Delete Event Item
- **URL**: `/events/{name}/eventItems/{id}`
- **Method**: `DELETE`

### Athlete Management

#### Get All Athletes
- **URL**: `/events/{name}/athletes`
- **Method**: `GET`

#### Get Athlete by ID
- **URL**: `/events/{name}/athletes/{id}`
- **Method**: `GET`

#### Create Athlete
- **URL**: `/events/{name}/athletes`
- **Method**: `POST`

#### Delete Athlete
- **URL**: `/events/{name}/athletes/{id}`
- **Method**: `DELETE`

### Additional Endpoints

#### Get Medal Tally by Country and Event
- **URL**: `/events/{name}/countries/{id}/tally`
- **Method**: `GET`

#### Get Event Item Records
- **URL**: `/events/{name}/eventItems/{id}/records`
- **Method**: `GET`

## Testing
Use **Postman** to test the APIs:
1. Open Postman and create a new request.
2. Select the appropriate HTTP method (GET, POST, etc.).
3. Enter the API URL (e.g., `http://localhost:8080/events/Sim/1`).
4. Send the request and inspect the response.

## Contributing

Feel free to contribute by submitting issues or pull requests. For detailed contribution guidelines, please refer to the project's contribution document.


### License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact
For any inquiries or issues, please contact [DKS2301](https://github.com/DKS2301).

