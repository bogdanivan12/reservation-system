# Reservation Management System

A reservation management system for events, built using **Spring Boot** and documented with **Swagger**.

---

## 📈 Business Requirements
1. Users can create and manage events.
2. Users can book seats for events.
3. Each event is linked to a location and a category.
4. Locations have a maximum number of available seats.
5. The system must prevent overbooking.
6. Users receive a unique ticket upon reservation.
7. Tickets can be validated at the event entrance.
8. Events can be deleted or modified by the organizers.
9. Locations can be added, modified, and deleted.
10. The system provides a complete API documentation using Swagger.

---

## 🎯 MVP - Core Features
1. **Event Management** – Full CRUD operations for the `Event` entity.
2. **Seat Reservation** – Full CRUD operations for the `Reservation` entity.
3. **Location Management** – Full CRUD operations for the `Location` entity.
4. **Ticket Management** – Full CRUD operations for the `Ticket` entity.
5. **API Documentation (Swagger)** – Complete documentation for all available endpoints.

---

## 📦 Entities and Relationships
The Reservation Management System includes the following entities and their relationships:

1. **User**
    - `id` (Long, Primary Key)
    - `name` (String)
    - `email` (String)
    - `phoneNumber` (String)
    - **Relationships:**
      - `User` ↔️ `Reservation` – One-to-Many (a user can make multiple reservations).

2. **Event**
   - `id` (Long, Primary Key)
   - `name` (String)
   - `date` (LocalDate)
   - `capacity` (Integer)
   - `availableSeats` (Integer)
   - **Relationships:**
     - `Event` ↔️ `Location` – Many-to-One (an event takes place at a single location).
     - `Event` ↔️ `Category` – Many-to-One (each event belongs to one category).
     - `Event` ↔️ `Reservation` – One-to-Many (an event can have multiple reservations).
     
3. **Reservation**
   - `id` (Long, Primary Key)
   - `numberOfSeats` (Integer)
   - `reservationDate` (LocalDateTime)
   - **Relationships:**
     - `Reservation` ↔️ `User` – Many-to-One (a reservation belongs to one user).
     - `Reservation` ↔️ `Event` – Many-to-One (a reservation is linked to one event).
     - `Reservation` ↔️ `Ticket` – One-to-One (each reservation generates a unique ticket).
     
4. **Ticket**
   - `id` (Long, Primary Key)
   - `ticketCode` (String)
   - `validated` (Boolean)
   - **Relationships:**
     - `Ticket` ↔️ `Reservation` – One-to-One (one ticket is generated per reservation).
     
5. **Location**
   - `id` (Long, Primary Key)
   - `name` (String)
   - `address` (String)
   - `capacity` (Integer)
   - **Relationships:**
     - `Location` ↔️ `Event` – One-to-Many (a location can host multiple events).
     
6. **Category**
   - `id` (Long, Primary Key)
   - `name` (String)
   - **Relationships:**
     - `Category` ↔️ `Event` – One-to-Many (a category can include multiple events).

---

## 📊 Entity Relationship Diagram (ERD)
For a visual representation, the relationships between the entities can be described as:

```plaintext
[User] 1 --- * [Reservation] * --- 1 [Event] 1 --- * [Location]
                     1                  *
                     |                  |
                     1                  1
                 [Ticket]           [Category]
```

---

## 🚀 Running the Application

### Requirements:
- Java 23
- Maven 3.8+
- IntelliJ IDEA (optional)

### Steps to Run:
```bash
# Clone the repository
git clone <repository-url>
cd reservation-management-system

# Run the application
./mvnw spring-boot:run
```

### Access the Swagger Interface:
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [OpenAPI Docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testing

Run unit tests using Maven:
```bash
./mvnw test
```

### Test Coverage:

- ✅ `UserService` – Fully tested
- ✅ `EventService` – Fully tested
- ✅ `ReservationService` – Fully tested
- ✅ `TicketService` – Fully tested
- ✅ `CategoryService` – Fully tested
- ✅ `UserController` – Fully tested
- ✅ `EventController` – Fully tested
- ✅ `ReservationController` – Fully tested
- ✅ `TicketController` – Fully tested
- ✅ `CategoryController` – Fully tested

---

## 📚 Technologies Used

- Java 23
- Spring Boot 3.4.1
- H2 Database (In-Memory)
- Jakarta Validation
- Maven
- JUnit 5
- Swagger 3.1.0

---

## 📌 Author

- **Name:** Bogdan Ivan
- **Program:** Master's Degree in Software Engineering (1st Year)
- **Faculty:** Faculty of Mathematics and Informatics, University of Bucharest
- **Project for the course:** Web Programming using Java Technologies