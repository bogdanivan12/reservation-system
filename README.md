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