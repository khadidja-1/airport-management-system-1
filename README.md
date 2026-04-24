# airport-management-system-1
A simple Airport Management System built with Java, demonstrating the 5 core OOP principles.

---

## Project Structure

```
airport-management-system/
├── AirportManagementSystem.java  
├── AirportManagementSystem.jar    
├── Dockerfile                    
└── README.md
```

---

## OOP Concepts Covered

| Concept | Where |
|---|---|
| **Encapsulation** | Private attributes + getters/setters in every class |
| **Abstraction**   | `AirportEntity` abstract class with 10 abstract methods |
| **Interfaces**    | `Reservable`, `Payable`, `Trackable` |
| **Inheritance**   | `Flight` → 5 subclasses · `Staff` → 5 subclasses |
| **Polymorphism**  | Method overriding + `FlightFactory` (Factory Pattern) |

---

## Classes Overview

**Interfaces:** `Reservable`, `Payable`, `Trackable`

**Abstract class:** `AirportEntity` (10 abstract methods)

**Main classes:** `AirportManager`, `Flight`, `Passenger`, `Ticket`, `Reservation`, `BoardingPass`

**Flight types:** `DomesticFlight`, `InternationalFlight`, `CargoFlight`, `CharterFlight`, `EmergencyFlight`

**Staff types:** `Pilot`, `CabinCrew`, `GroundStaff`, `SecurityOfficer`, `MaintenanceStaff`

**Support classes:** `Airport`, `Terminal`, `Gate`, `Baggage`, `SecurityCheck`, `Payment`, `Schedule`, `Notification`

**Utilities:** `ReportGenerator`, `InputValidator`, `FlightFactory`

---

## How to Run

### Option 1 — Java (JAR)

**Requirement:** Java 8 or higher

```bash
java -jar AirportManagementSystem.jar
```

### Option 2 — Docker

**Requirement:** Docker installed

```bash
# Build the image
docker build -t airport-system .

# Run (interactive mode needed for user input)
docker run -it airport-system
```

---

## Program Flow

When you run the program, it guides you through these steps:

1. Enter passenger details (name, passport, nationality, phone)
2. Automatic security check
3. Choose a flight from the list
4. Select seat and class (Economy / Business / First)
5. Choose payment method
6. Check in baggage
7. Receive boarding pass
8. View reports and system demo
