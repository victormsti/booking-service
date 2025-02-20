
# Bookstar - Booking Management System

## Overview
**Bookstar** is a booking management system designed to facilitate property owners and users to manage room bookings. This system allows property owners to create blocks, update bookings, and cancel reservations. Clients can search for available rooms, make bookings, and view or cancel their reservations.



## Features

- **Authentication**: Users can authenticate via JWT tokens (using fixed credentials for testing).
- **Room Management**: Property owners can create, update, and delete blocks or bookings.
- **Room Availability**: Clients can search for available rooms based on specific parameters.
- **Booking Management**: Clients can create, update, and cancel bookings, while property owners can manage booking blocks.
- **Error Handling**: Graceful error responses for various failure scenarios.

## Available Users
- **Property Owner**:  
  Username: `property_admin`  
  Password: `admin`

- **Client User**:  
  Username: `test_user`  
  Password: `123456`

## Authentication
To authenticate and retrieve a JWT token, call the following endpoint:

**POST /api/v1/auth/login**
```json
{
    "username": "test_user",
    "password": "123456"
}
```

Response Example:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0X3VzZXIiLCJpYXQiOjE3NDAwNTg2MDAsImV4cCI6MTc0MDA2MjIwMH0.qvnz1tYysZ7Q-gAH83FWzMuiVv0l-X2xGin_vf7lBYY"
}
```
You will need to use the token in the `Authorization` header as `Bearer <your-token>` for accessing other endpoints.

## Architecture

- **Controller Layer:** Handles HTTP requests and responses, delegates logic to the service layer.

- **Service Layer:** Contains business logic and rules, interacts with repositories.

- **Repository Layer:** Manages data persistence and retrieval.

- **Model Layer:** Houses entity classes representing database structures.

- **DTO Layer:** Transfers data between layers.

- **Exception Handling:** Centralized error handling mechanism.

## API Endpoints

### 1. **Get Available Rooms**
**GET /api/v1/rooms**
Fetch all available rooms based on parameters like property type, number of people, and check-in/check-out dates.

Request Example:
```bash
GET localhost:8080/api/v1/rooms?propertyType=HOTEL&quantityOfPeople=2&checkInDate=2025-04-18&checkOutDate=2025-04-22
```

Response Example:
```json
{
    "content": [
        {
            "id": 1,
            "property": {
                "id": 1,
                "name": "Hotel Paradise",
                "type": "HOTEL",
                "user": {
                    "id": 2,
                    "username": "property_admin",
                    "email": "admin@example.com",
                    "type": "PROPERTY_ADMIN"
                }
            },
            "name": "Deluxe Suite",
            "type": "DOUBLE",
            "capacity": 2,
            "pricePerNight": 150.00
        }
    ],
    "pageable": { ... },
    "totalPages": 1,
    "totalElements": 1
}
```

### 2. **Make a Booking**
**POST /api/v1/bookings**
Create a booking with selected room and guest details.

Request Example:
```json
{
    "roomId": 1,
    "quantityOfPeople": 2,
    "checkInDate": "2025-04-18", 
    "checkOutDate": "2025-04-22",
    "guests": [
        {
            "firstName": "John",
            "lastName": "Doe",
            "birthDate": "1990-05-15",
            "mainGuest": true
        },
        {
            "firstName": "Jane",
            "lastName": "Doe",
            "birthDate": "1992-08-22",
            "mainGuest": false
        }
    ]
}
```

Response Example:
```json
{
    "id": 2,
    "user": {
        "id": 1,
        "username": "test_user",
        "email": "user@example.com",
        "type": "CLIENT"
    },
    "room": {
        "id": 1,
        "property": {
            "id": 1,
            "name": "Hotel Paradise",
            "type": "HOTEL",
            "user": {
                "id": 2,
                "username": "property_admin",
                "email": "admin@example.com",
                "type": "PROPERTY_ADMIN"
            }
        },
        "name": "Deluxe Suite",
        "type": "DOUBLE",
        "capacity": 2,
        "pricePerNight": 150.00
    },
    "status": "CONFIRMED",
    "type": "GUEST",
    "quantityOfPeople": 2,
    "checkInDate": "2025-04-18",
    "checkOutDate": "2025-04-22",
    "finalPrice": 600.00,
    "paymentStatus": "PAID",
    "guests": [
        {
            "id": 3,
            "firstName": "John",
            "lastName": "Doe",
            "birthDate": "1990-05-15",
            "mainGuest": true
        },
        {
            "id": 4,
            "firstName": "Jane",
            "lastName": "Doe",
            "birthDate": "1992-08-22",
            "mainGuest": false
        }
    ]
}
```

### 3. **Update an Existing Booking**
**PUT /api/v1/bookings/{id}**
Update an existing booking with new details.

Request Example:
```json
{
    "roomId": 1,
    "quantityOfPeople": 2,
    "checkInDate": "2025-04-06",
    "checkOutDate": "2025-04-08",
    "guests": [
        {
            "firstName": "John",
            "lastName": "Doe2",
            "birthDate": "1991-05-15",
            "mainGuest": false
        },
        {
            "firstName": "Jane",
            "lastName": "Doe2",
            "birthDate": "1993-08-22",
            "mainGuest": true
        }
    ]
}
```

### 4. **Cancel a Booking**
**PUT /api/v1/bookings/cancellations/{id}**
Cancel a booking by booking ID.

Request Example:
```bash
PUT localhost:8080/api/v1/bookings/cancellations/2
```

### 5. **Rebook a Canceled Booking**
**PUT /api/v1/bookings/{id}/rebook**
Rebook a canceled booking.

Request Example:
```bash
PUT localhost:8080/api/v1/bookings/2/rebook
```

### 6. **Create a Block**
**POST /api/v1/bookings/blocks**
Create a block to make a room unavailable for bookings.

Request Example:
```json
{
    "roomId": 1,
    "checkInDate": "2025-04-09", 
    "checkOutDate": "2025-04-10"
}
```

### 7. **Update a Block**
**PUT /api/v1/bookings/blocks/{id}**
Update an existing block's details.

Request Example:
```json
{
    "roomId": 1,
    "checkInDate": "2025-04-09", 
    "checkOutDate": "2025-04-11"
}
```

### 8. **Delete a Booking or Block**
**DELETE /api/v1/bookings/{id}**
Delete an existing booking or block by booking ID.

Request Example:
```bash
DELETE localhost:8080/api/v1/bookings/2
```

## Error Handling
- **400 Bad Request**: Invalid input or request format.
- **404 Not Found**: Requested resource not found.
- **409 Conflict**: Another data was found, so we cannot proceed.
- **500 Internal Server Error**: Unexpected error occurred.

## Local Setup

### Prerequisites
- Java 17 or higher
- Gradle

### Building the app
- Run the following command:
  - ```gradle clean build```

- If you are using the Gradle wrapper, you can run:
  - ```./gradlew clean build```

### Running tests

This project has unit and integration tests

To run all tests, use the following command:
- ```gradle test```

- If you are using the Gradle wrapper, you can run:
  - ```./gradlew test```

- To run a specific test class, you can use the following command:
  - ```gradle test --tests YourTestClassName```

- If you are using the Gradle wrapper, you can run:
  - ```./gradlew test --tests YourTestClassName```

### Running the App via Command Line

Alternatively, we can run the app via command line by the following commands

```gradle bootRun```

**Running with Gradle Wrapper:** If you don’t have Gradle installed, you can use the Gradle wrapper:

```./gradlew bootRun```

If you have any questions, please feel free to reach out to me at: [victormsti@gmail.com](mailto:victormsti@gmail.com)