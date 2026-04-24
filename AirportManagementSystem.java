import java.util.*;
import java.time.*;
import java.time.format.*;

// ==================== INTERFACES ====================

interface Reservable {
    void createReservation();
    void cancelReservation();
    void modifyReservation();
}

interface Payable {
    void processPayment(double amount, String method);
    double calculateAmount();
    void generateReceipt();
}

interface Trackable {
    void trackStatus();
    void updateLocation(String location);
    String getCurrentLocation();
}

// ==================== ABSTRACT CLASS ====================

abstract class AirportEntity {
    private String entityId;
    private String name;
    private String status;
    private String createdDate;
    private String lastUpdated;

    public AirportEntity() {
        this.entityId    = "ENT-001";
        this.name        = "Unknown";
        this.status      = "INACTIVE";
        this.createdDate = LocalDate.now().toString();
        this.lastUpdated = LocalDate.now().toString();
    }

    public AirportEntity(String entityId, String name, String status) {
        this.entityId    = entityId;
        this.name        = name;
        this.status      = status;
        this.createdDate = LocalDate.now().toString();
        this.lastUpdated = LocalDate.now().toString();
    }

    public String getEntityId()            { return entityId; }
    public void   setEntityId(String id)   { this.entityId = id; }
    public String getName()                { return name; }
    public void   setName(String name)     { this.name = name; }
    public String getStatus()              { return status; }
    public void   setStatus(String status) { this.status = status; this.lastUpdated = LocalDate.now().toString(); }
    public String getCreatedDate()         { return createdDate; }
    public String getLastUpdated()         { return lastUpdated; }

    public abstract boolean validateEntity();
    public abstract void    activateEntity();
    public abstract void    deactivateEntity();
    public abstract void    updateDetails(String field, String value);
    public abstract void    generateReport();
    public abstract void    logActivity(String activity);
    public abstract String  checkStatus();
    public abstract void    archiveEntity();
    public abstract void    restoreEntity();
    public abstract void    displaySummary();

    @Override
    public String toString() {
        return "ID: " + entityId + " | Name: " + name + " | Status: " + status;
    }
}

// ==================== AIRPORT MANAGER ====================

class AirportManager extends AirportEntity implements Reservable, Payable, Trackable {
    private String airportName;
    private int    terminalNumber;
    private String managerName;
    private String currentLocation;

    public AirportManager() { super(); }

    public AirportManager(String id, String name, String airportName, int terminal, String managerName) {
        super(id, name, "ACTIVE");
        this.airportName     = airportName;
        this.terminalNumber  = terminal;
        this.managerName     = managerName;
        this.currentLocation = "Terminal " + terminal;
    }

    public String getAirportName()    { return airportName; }
    public int    getTerminalNumber() { return terminalNumber; }
    public String getManagerName()    { return managerName; }

    @Override public boolean validateEntity()                        { return managerName != null && !managerName.isEmpty(); }
    @Override public void    activateEntity()                        { setStatus("ACTIVE");   System.out.println("Manager activated."); }
    @Override public void    deactivateEntity()                      { setStatus("INACTIVE"); System.out.println("Manager deactivated."); }
    @Override public void    updateDetails(String field, String val) { System.out.println("Updated " + field + " = " + val); }
    @Override public void    generateReport()                        { System.out.println("Manager: " + managerName + " | Airport: " + airportName); }
    @Override public void    logActivity(String activity)            { System.out.println("[LOG] " + activity); }
    @Override public String  checkStatus()                           { return getStatus(); }
    @Override public void    archiveEntity()                         { setStatus("ARCHIVED"); }
    @Override public void    restoreEntity()                         { setStatus("ACTIVE"); }
    @Override public void    displaySummary()                        { System.out.println("Manager: " + managerName + " | Terminal: " + terminalNumber); }

    @Override public void   createReservation()                      { System.out.println("Reservation created."); }
    @Override public void   cancelReservation()                      { System.out.println("Reservation cancelled."); }
    @Override public void   modifyReservation()                      { System.out.println("Reservation modified."); }

    @Override public void   processPayment(double amount, String m)  { System.out.printf("Payment: $%.2f via %s%n", amount, m); }
    @Override public double calculateAmount()                         { return 0; }
    @Override public void   generateReceipt()                         { System.out.println("Receipt generated."); }

    @Override public void   trackStatus()                             { System.out.println("Status: " + getStatus()); }
    @Override public void   updateLocation(String loc)               { this.currentLocation = loc; }
    @Override public String getCurrentLocation()                      { return currentLocation; }

    @Override public String toString() {
        return "Manager: " + managerName + " | Airport: " + airportName + " | Terminal: " + terminalNumber;
    }
}

// ==================== FLIGHT ====================

class Flight {
    private String flightId;
    private String airlineName;
    private String departureLocation;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int    capacity;
    private int    bookedSeats;

    public Flight() {}

    public Flight(String flightId, String airlineName, String departure, String destination,
                  String departureTime, String arrivalTime, int capacity) {
        this.flightId          = flightId;
        this.airlineName       = airlineName;
        this.departureLocation = departure;
        this.destination       = destination;
        this.departureTime     = departureTime;
        this.arrivalTime       = arrivalTime;
        this.capacity          = capacity;
        this.bookedSeats       = 0;
    }

    public String  getFlightId()          { return flightId; }
    public String  getAirlineName()       { return airlineName; }
    public String  getDepartureLocation() { return departureLocation; }
    public String  getDestination()       { return destination; }
    public String  getDepartureTime()     { return departureTime; }
    public String  getArrivalTime()       { return arrivalTime; }
    public int     getCapacity()          { return capacity; }
    public int     getBookedSeats()       { return bookedSeats; }
    public boolean isAvailable()          { return bookedSeats < capacity; }
    public void    bookSeat()             { if (isAvailable()) bookedSeats++; }
    public String  getFlightType()        { return "Flight"; }
    public int     calculateFlightDuration() { return 120; }

    @Override public String toString() {
        return "[" + getFlightType() + "] " + flightId + " | " + departureLocation + " -> " + destination
             + " | Dep: " + departureTime + " | Seats: " + bookedSeats + "/" + capacity;
    }
}

class DomesticFlight extends Flight {
    private String regionCode;
    public DomesticFlight(String id, String airline, String dep, String dest, String depT, String arrT, int cap, String region) {
        super(id, airline, dep, dest, depT, arrT, cap);
        this.regionCode = region;
    }
    public String getRegionCode()        { return regionCode; }
    @Override public String getFlightType()  { return "Domestic"; }
    @Override public String getDestination() { return super.getDestination() + " [" + regionCode + "]"; }
    @Override public String toString()       { return super.toString() + " | Region: " + regionCode; }
}

class InternationalFlight extends Flight {
    private boolean passportRequired;
    public InternationalFlight(String id, String airline, String dep, String dest, String depT, String arrT, int cap, boolean passport) {
        super(id, airline, dep, dest, depT, arrT, cap);
        this.passportRequired = passport;
    }
    public boolean isPassportRequired()          { return passportRequired; }
    @Override public String getFlightType()      { return "International"; }
    @Override public int calculateFlightDuration(){ return super.calculateFlightDuration() + 30; }
    @Override public String toString()           { return super.toString() + " | Passport: " + (passportRequired ? "Yes" : "No"); }
}

class CargoFlight extends Flight {
    private double cargoWeightLimit;
    public CargoFlight(String id, String airline, String dep, String dest, String depT, String arrT, int cap, double limit) {
        super(id, airline, dep, dest, depT, arrT, cap);
        this.cargoWeightLimit = limit;
    }
    public double getCargoWeightLimit()     { return cargoWeightLimit; }
    @Override public String getFlightType() { return "Cargo"; }
    @Override public String getAirlineName(){ return "[CARGO] " + super.getAirlineName(); }
    @Override public String toString()      { return super.toString() + " | Max Cargo: " + cargoWeightLimit + "kg"; }
}

class CharterFlight extends Flight {
    private String clientName;
    public CharterFlight(String id, String airline, String dep, String dest, String depT, String arrT, int cap, String client) {
        super(id, airline, dep, dest, depT, arrT, cap);
        this.clientName = client;
    }
    public String getClientName()             { return clientName; }
    @Override public String  getFlightType()  { return "Charter"; }
    @Override public boolean isAvailable()    { return false; }
    @Override public String  toString()       { return super.toString() + " | Client: " + clientName; }
}

class EmergencyFlight extends Flight {
    private int emergencyLevel;
    public EmergencyFlight(String id, String airline, String dep, String dest, String depT, String arrT, int cap, int level) {
        super(id, airline, dep, dest, depT, arrT, cap);
        this.emergencyLevel = level;
    }
    public int getEmergencyLevel()               { return emergencyLevel; }
    @Override public String getFlightType()      { return "Emergency"; }
    @Override public int calculateFlightDuration(){ return 0; }
    @Override public String toString()           { return super.toString() + " | Level: " + emergencyLevel; }
}

// ==================== PASSENGER ====================

class Passenger {
    private String passengerId;
    private String fullName;
    private String passportNumber;
    private String nationality;
    private String phoneNumber;

    public Passenger(String id, String fullName, String passport, String nationality, String phone) {
        this.passengerId = id; this.fullName = fullName; this.passportNumber = passport;
        this.nationality = nationality; this.phoneNumber = phone;
    }

    public String getPassengerId()    { return passengerId; }
    public String getFullName()       { return fullName; }
    public String getPassportNumber() { return passportNumber; }
    public String getNationality()    { return nationality; }
    public String getPhoneNumber()    { return phoneNumber; }

    @Override public String toString() {
        return "Passenger: " + fullName + " | Passport: " + passportNumber + " | Nationality: " + nationality;
    }
}

// ==================== TICKET ====================

class Ticket {
    private String    ticketId;
    private Passenger passenger;
    private Flight    flight;
    private String    seatNumber;
    private String    classType;
    private double    price;

    public Ticket(String id, Passenger passenger, Flight flight, String seat, String classType) {
        this.ticketId = id; this.passenger = passenger; this.flight = flight;
        this.seatNumber = seat; this.classType = classType; this.price = calculateTicketPrice();
    }

    public double calculateTicketPrice() {
        return switch (classType.toUpperCase()) {
            case "BUSINESS" -> 500.0;
            case "FIRST"    -> 800.0;
            default         -> 200.0;
        };
    }

    public void generateTicketDetails() {
        System.out.println("\n--- TICKET ---");
        System.out.println("Ticket ID : " + ticketId);
        System.out.println("Passenger : " + passenger.getFullName());
        System.out.println("Flight    : " + flight.getFlightId());
        System.out.println("Route     : " + flight.getDepartureLocation() + " -> " + flight.getDestination());
        System.out.println("Seat      : " + seatNumber + " | Class: " + classType);
        System.out.printf ("Price     : $%.2f%n", price);
    }

    public String    getTicketId()   { return ticketId; }
    public Passenger getPassenger()  { return passenger; }
    public Flight    getFlight()     { return flight; }
    public String    getSeatNumber() { return seatNumber; }
    public String    getClassType()  { return classType; }
    public double    getPrice()      { return price; }

    @Override public String toString() {
        return "Ticket: " + ticketId + " | " + passenger.getFullName() + " | Seat: " + seatNumber + " | $" + price;
    }
}

// ==================== RESERVATION ====================

class Reservation {
    private String    reservationId;
    private Passenger passenger;
    private Flight    flight;
    private String    bookingDate;
    private String    status;

    public Reservation(String id, Passenger passenger, Flight flight) {
        this.reservationId = id; this.passenger = passenger; this.flight = flight;
        this.bookingDate = LocalDate.now().toString(); this.status = "PENDING";
    }

    public void confirmReservation() {
        if (!flight.isAvailable()) { System.out.println("ERROR: No seats available."); return; }
        this.status = "CONFIRMED";
        flight.bookSeat();
        System.out.println("Reservation " + reservationId + " CONFIRMED.");
    }

    public void cancelReservation() { this.status = "CANCELLED"; System.out.println("Reservation CANCELLED."); }
    public void updateReservation(Flight f) { this.flight = f; System.out.println("Reservation updated to flight: " + f.getFlightId()); }

    public String getReservationId() { return reservationId; }
    public String getStatus()        { return status; }

    @Override public String toString() {
        return "Reservation: " + reservationId + " | " + passenger.getFullName() + " | " + flight.getFlightId() + " | " + status;
    }
}

// ==================== BOARDING PASS ====================

class BoardingPass {
    private String boardingPassId;
    private Ticket ticket;
    private String gateNumber;
    private String boardingTime;

    public BoardingPass(String id, Ticket ticket, String gate, String boardingTime) {
        this.boardingPassId = id; this.ticket = ticket; this.gateNumber = gate; this.boardingTime = boardingTime;
    }

    public void print() {
        System.out.println("\n--- BOARDING PASS ---");
        System.out.println("BP ID     : " + boardingPassId);
        System.out.println("Passenger : " + ticket.getPassenger().getFullName());
        System.out.println("Flight    : " + ticket.getFlight().getFlightId());
        System.out.println("Route     : " + ticket.getFlight().getDepartureLocation() + " -> " + ticket.getFlight().getDestination());
        System.out.println("Seat      : " + ticket.getSeatNumber() + " | Class: " + ticket.getClassType());
        System.out.println("Gate      : " + gateNumber);
        System.out.println("Boarding  : " + boardingTime);
    }

    public String getBoardingPassId() { return boardingPassId; }
    public String getGateNumber()     { return gateNumber; }
}

// ==================== STAFF ====================

class Staff {
    private String staffId;
    private String name;
    private String role;
    private double salary;

    public Staff(String id, String name, String role, double salary) {
        this.staffId = id; this.name = name; this.role = role; this.salary = salary;
    }

    public String getStaffId() { return staffId; }
    public String getName()    { return name; }
    public String getRole()    { return role; }
    public double getSalary()  { return salary; }

    @Override public String toString() { return staffId + " | " + name + " | " + role + " | $" + salary; }
}

class Pilot extends Staff {
    private int flightHours;
    public Pilot(String id, String name, double salary, int hours) { super(id, name, "Pilot", salary); this.flightHours = hours; }
    @Override public String toString() { return super.toString() + " | Hours: " + flightHours; }
}

class CabinCrew extends Staff {
    private String languages;
    public CabinCrew(String id, String name, double salary, String languages) { super(id, name, "Cabin Crew", salary); this.languages = languages; }
    @Override public String toString() { return super.toString() + " | Languages: " + languages; }
}

class GroundStaff extends Staff {
    private String department;
    public GroundStaff(String id, String name, double salary, String dept) { super(id, name, "Ground Staff", salary); this.department = dept; }
    @Override public String toString() { return super.toString() + " | Dept: " + department; }
}

class SecurityOfficer extends Staff {
    private int securityLevel;
    public SecurityOfficer(String id, String name, double salary, int level) { super(id, name, "Security", salary); this.securityLevel = level; }
    @Override public String toString() { return super.toString() + " | Level: " + securityLevel; }
}

class MaintenanceStaff extends Staff {
    private String specialization;
    public MaintenanceStaff(String id, String name, double salary, String spec) { super(id, name, "Maintenance", salary); this.specialization = spec; }
    @Override public String toString() { return super.toString() + " | Spec: " + specialization; }
}

// ==================== AIRPORT / TERMINAL / GATE ====================

class Airport {
    private String airportId, name, location;
    private int numberOfTerminals;
    public Airport(String id, String name, String location, int terminals) {
        this.airportId = id; this.name = name; this.location = location; this.numberOfTerminals = terminals;
    }
    public String getName()     { return name; }
    public String getLocation() { return location; }
    @Override public String toString() { return "Airport: " + name + " | " + location + " | Terminals: " + numberOfTerminals; }
}

class Terminal {
    private String terminalId, name;
    private int capacity;
    public Terminal(String id, String name, int capacity) { this.terminalId = id; this.name = name; this.capacity = capacity; }
    public String getName() { return name; }
    @Override public String toString() { return "Terminal: " + name + " | Capacity: " + capacity; }
}

class Gate {
    private String gateId, status;
    private Terminal terminal;
    public Gate(String id, Terminal terminal, String status) { this.gateId = id; this.terminal = terminal; this.status = status; }
    public String getGateId() { return gateId; }
    public String getStatus() { return status; }
    @Override public String toString() { return "Gate: " + gateId + " | " + terminal.getName() + " | " + status; }
}

// ==================== ADVANCED CLASSES ====================

class Baggage {
    private String baggageId, status;
    private double weight;
    private Passenger owner;
    public Baggage(String id, double weight, Passenger owner) { this.baggageId = id; this.weight = weight; this.owner = owner; this.status = "CHECKED_IN"; }
    public void setStatus(String s) { this.status = s; }
    @Override public String toString() { return "Baggage: " + baggageId + " | " + weight + "kg | " + owner.getFullName() + " | " + status; }
}

class SecurityCheck {
    private String checkId, status, remarks;
    private Passenger passenger;
    public SecurityCheck(String id, Passenger p) { this.checkId = id; this.passenger = p; this.status = "PENDING"; this.remarks = ""; }
    public void performCheck(boolean passed, String remarks) {
        this.status = passed ? "CLEARED" : "FAILED"; this.remarks = remarks;
        System.out.println("Security [" + passenger.getFullName() + "]: " + status + " - " + remarks);
    }
    public String getStatus() { return status; }
}

class Payment {
    private String paymentId, paymentMethod, paymentStatus;
    private double amount;
    public Payment(String id, double amount, String method) { this.paymentId = id; this.amount = amount; this.paymentMethod = method; this.paymentStatus = "PENDING"; }
    public void process() { this.paymentStatus = "SUCCESS"; System.out.printf("Payment $%.2f via %s: %s%n", amount, paymentMethod, paymentStatus); }
    public void printReceipt() {
        System.out.println("\n--- PAYMENT RECEIPT ---");
        System.out.println("Payment ID : " + paymentId);
        System.out.printf ("Amount     : $%.2f%n", amount);
        System.out.println("Method     : " + paymentMethod);
        System.out.println("Status     : " + paymentStatus);
        System.out.println("Date       : " + LocalDate.now());
    }
}

class Schedule {
    private String flightId, gateId, departureTime;
    public Schedule(String flightId, String gateId, String departureTime) { this.flightId = flightId; this.gateId = gateId; this.departureTime = departureTime; }
    @Override public String toString() { return "Schedule | Flight: " + flightId + " | Gate: " + gateId + " | " + departureTime; }
}

class Notification {
    private String message, recipient;
    public Notification(String message, String recipient) { this.message = message; this.recipient = recipient; }
    public void send() { System.out.println("[Notification -> " + recipient + "] " + message); }
}

// ==================== REPORT GENERATOR ====================

class ReportGenerator {
    public void generateDailyFlightsReport(List<Flight> flights) {
        System.out.println("\n--- DAILY FLIGHTS (" + flights.size() + ") ---");
        for (Flight f : flights) System.out.println("  " + f);
    }
    public void generatePassengerReport(List<Passenger> passengers) {
        System.out.println("--- PASSENGERS: " + passengers.size() + " ---");
    }
    public void generateRevenueReport(List<Ticket> tickets) {
        double total = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        System.out.printf("--- REVENUE: $%.2f (%d tickets) ---%n", total, tickets.size());
    }
    public void generateDelayReport(List<Flight> delayed) {
        System.out.println("--- DELAYED FLIGHTS: " + delayed.size() + " ---");
    }
}

// ==================== INPUT VALIDATOR ====================

class InputValidator {
    public static boolean isNotEmpty(String value, String field) {
        if (value == null || value.isBlank()) { System.out.println("ERROR: " + field + " cannot be empty."); return false; }
        return true;
    }
    public static boolean isValidPassport(String p) {
        if (!p.matches("[A-Z0-9]{6,9}")) { System.out.println("ERROR: Passport must be 6-9 uppercase letters/digits."); return false; }
        return true;
    }
    public static boolean isValidPhone(String p) {
        if (!p.matches("\\d{10,15}")) { System.out.println("ERROR: Phone must be 10-15 digits."); return false; }
        return true;
    }
    public static boolean isValidSeat(String s) {
        if (!s.matches("[A-Z]\\d{1,2}")) { System.out.println("ERROR: Seat must be letter + 1-2 digits (e.g. A12)."); return false; }
        return true;
    }
    public static boolean isValidClassType(String c) {
        return c.equalsIgnoreCase("ECONOMY") || c.equalsIgnoreCase("BUSINESS") || c.equalsIgnoreCase("FIRST");
    }
    public static int readInt(Scanner sc) {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("ERROR: Enter a valid number: "); }
        }
    }
}

// ==================== FLIGHT FACTORY ====================

class FlightFactory {
    public static Flight createFlight(String type, String id, String airline,
                                      String dep, String dest, String depT, String arrT, int cap) {
        return switch (type.toUpperCase()) {
            case "DOMESTIC"      -> new DomesticFlight(id, airline, dep, dest, depT, arrT, cap, "DOM");
            case "INTERNATIONAL" -> new InternationalFlight(id, airline, dep, dest, depT, arrT, cap, true);
            case "CARGO"         -> new CargoFlight(id, airline, dep, dest, depT, arrT, cap, 50000);
            case "CHARTER"       -> new CharterFlight(id, airline, dep, dest, depT, arrT, cap, "VIP Client");
            case "EMERGENCY"     -> new EmergencyFlight(id, airline, dep, dest, depT, arrT, cap, 3);
            default              -> throw new IllegalArgumentException("Unknown flight type: " + type);
        };
    }
}

// ==================== MAIN CLASS ====================

public class AirportManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static String readNonEmpty(String prompt, String field) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (InputValidator.isNotEmpty(val, field)) return val;
        }
    }

    static String readPassport() {
        while (true) {
            System.out.print("Passport (6-9 uppercase letters/digits): ");
            String val = sc.nextLine().trim().toUpperCase();
            if (InputValidator.isValidPassport(val)) return val;
        }
    }

    static String readPhone() {
        while (true) {
            System.out.print("Phone (10-15 digits): ");
            String val = sc.nextLine().trim();
            if (InputValidator.isValidPhone(val)) return val;
        }
    }

    static String readClassType() {
        while (true) {
            System.out.print("Class (Economy / Business / First): ");
            String val = sc.nextLine().trim();
            if (InputValidator.isValidClassType(val)) return val.toUpperCase();
            System.out.println("ERROR: Enter Economy, Business, or First.");
        }
    }

    static String readSeat(Set<String> usedSeats) {
        while (true) {
            System.out.print("Seat number (e.g. A12): ");
            String val = sc.nextLine().trim().toUpperCase();
            if (!InputValidator.isValidSeat(val)) continue;
            if (usedSeats.contains(val)) { System.out.println("ERROR: Seat already taken."); continue; }
            return val;
        }
    }

    public static void main(String[] args) {

        System.out.println("=== AIRPORT MANAGEMENT SYSTEM ===\n");

        // Setup
        Airport  airport  = new Airport("A01", "Kigali International Airport", "Kigali, Rwanda", 2);
        Terminal terminal = new Terminal("T1", "Terminal 1", 500);
        Gate     gate     = new Gate("G1", terminal, "OPEN");

        AirportManager manager = new AirportManager("M01", "Manager", "Kigali Intl", 1, "Jean Damascene");
        manager.activateEntity();

        List<Flight> flights = new ArrayList<>();
        flights.add(FlightFactory.createFlight("DOMESTIC",     "RW101", "Rwandair",  "Kigali", "Butare",  "08:00", "09:00", 120));
        flights.add(FlightFactory.createFlight("INTERNATIONAL","AF202", "Air France","Kigali", "Paris",   "10:00", "18:00", 250));
        flights.add(FlightFactory.createFlight("INTERNATIONAL","ET303", "Ethiopian", "Kigali", "Nairobi", "12:00", "14:00",  80));

        List<Passenger> allPassengers = new ArrayList<>();
        List<Ticket>    allTickets    = new ArrayList<>();
        Set<String>     usedSeats     = new HashSet<>();

        // Step 1: Passenger
        System.out.println("--- STEP 1: PASSENGER DETAILS ---");
        String fullName    = readNonEmpty("Full Name: ", "Full Name");
        String passport    = readPassport();
        String nationality = readNonEmpty("Nationality: ", "Nationality");
        String phone       = readPhone();

        Passenger passenger = new Passenger("PAX-001", fullName, passport, nationality, phone);
        allPassengers.add(passenger);
        System.out.println("\n" + passenger);

        // Security Check
        System.out.println("\n--- SECURITY CHECK ---");
        SecurityCheck check = new SecurityCheck("SEC-001", passenger);
        check.performCheck(true, "All clear.");
        if (!check.getStatus().equals("CLEARED")) { System.out.println("Not cleared. Exiting."); sc.close(); return; }

        // Step 2: Flight Selection
        System.out.println("\n--- STEP 2: FLIGHT SELECTION ---");
        for (int i = 0; i < flights.size(); i++) System.out.println("[" + (i+1) + "] " + flights.get(i));

        int choice;
        while (true) {
            System.out.print("Choose flight [1-" + flights.size() + "]: ");
            choice = InputValidator.readInt(sc);
            if (choice >= 1 && choice <= flights.size()) break;
            System.out.println("ERROR: Invalid choice.");
        }
        Flight selected = flights.get(choice - 1);
        if (!selected.isAvailable()) { System.out.println("ERROR: Flight not available."); sc.close(); return; }
        System.out.println("Selected: " + selected);

        // Step 3: Booking
        System.out.println("\n--- STEP 3: BOOKING ---");
        String classType  = readClassType();
        String seatNumber = readSeat(usedSeats);
        usedSeats.add(seatNumber);

        Reservation reservation = new Reservation("RES-001", passenger, selected);
        reservation.confirmReservation();

        Ticket ticket = new Ticket("TKT-001", passenger, selected, seatNumber, classType);
        allTickets.add(ticket);
        ticket.generateTicketDetails();

        // Step 4: Payment
        System.out.println("\n--- STEP 4: PAYMENT ---");
        System.out.println("1) Credit Card   2) Cash   3) Mobile Money");
        int payChoice;
        while (true) {
            System.out.print("Choose method [1-3]: ");
            payChoice = InputValidator.readInt(sc);
            if (payChoice >= 1 && payChoice <= 3) break;
            System.out.println("ERROR: Choose 1, 2, or 3.");
        }
        String method = switch (payChoice) { case 1 -> "Credit Card"; case 2 -> "Cash"; default -> "Mobile Money"; };

        Payment payment = new Payment("PAY-001", ticket.getPrice(), method);
        payment.process();
        payment.printReceipt();

        // Baggage
        System.out.println("\n--- BAGGAGE CHECK-IN ---");
        double baggageWeight = 0;
        while (true) {
            System.out.print("Baggage weight in kg (max 30): ");
            try {
                baggageWeight = Double.parseDouble(sc.nextLine().trim());
                if (baggageWeight > 0 && baggageWeight <= 30) break;
                System.out.println("ERROR: Weight must be between 0.1 and 30.");
            } catch (NumberFormatException e) { System.out.println("ERROR: Enter a valid number."); }
        }
        Baggage baggage = new Baggage("BAG-001", baggageWeight, passenger);
        System.out.println(baggage);

        // Boarding Pass
        BoardingPass bp = new BoardingPass("BP-001", ticket, gate.getGateId(), "45 min before departure");
        bp.print();

        new Notification("Boarding pass ready. Gate: " + gate.getGateId(), passenger.getFullName()).send();

        // Reports
        System.out.println("\n--- REPORTS ---");
        ReportGenerator reporter = new ReportGenerator();
        reporter.generateDailyFlightsReport(flights);
        reporter.generatePassengerReport(allPassengers);
        reporter.generateRevenueReport(allTickets);
        reporter.generateDelayReport(new ArrayList<>());

        // Polymorphism Demo
        System.out.println("\n--- POLYMORPHISM DEMO ---");
        for (Flight f : flights)
            System.out.println("Type: " + f.getFlightType() + " | Duration: " + f.calculateFlightDuration() + " min");

        // Staff Demo
        System.out.println("\n--- STAFF ---");
        List<Staff> staff = new ArrayList<>();
        staff.add(new Pilot("S1", "Capt. Kagame", 8500, 12000));
        staff.add(new CabinCrew("S2", "Marie Claire", 3200, "French, English"));
        staff.add(new GroundStaff("S3", "Patrick N.", 2500, "Baggage"));
        staff.add(new SecurityOfficer("S4", "Claude M.", 3000, 3));
        staff.add(new MaintenanceStaff("S5", "Eric K.", 4000, "Avionics"));
        for (Staff s : staff) System.out.println(s);

        System.out.println("\n=== BOOKING COMPLETE. HAVE A SAFE FLIGHT! ===");
        sc.close();
    }
}
