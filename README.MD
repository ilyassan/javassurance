# JavaAssurance

A comprehensive insurance management system built in Java for managing clients, advisors, contracts, and insurance incidents.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Reference](#api-reference)
- [Contributing](#contributing)

## 🌟 Overview

JavaAssurance is a console-based insurance management application that provides a complete solution for insurance companies to manage their operations. The system handles the entire insurance workflow from client registration to incident reporting and claims management.

## ✨ Features

### 👥 Client Management
- Create new clients with personal information
- Search clients by ID
- Delete clients with confirmation
- View all clients ordered by family name
- Find clients with incidents above a specific cost threshold
- List all clients assigned to a specific advisor

### 👨‍💼 Advisor Management
- Create new advisors
- Search advisors by ID
- Delete advisors with confirmation
- View all clients assigned to a specific advisor

### 📄 Contract Management
- Create insurance contracts (Car, Property, Health)
- Search contracts by ID
- Delete contracts with confirmation
- View contracts by client
- Support for contract start/end dates

### 🚨 Incident Management
- Report new incidents (Car Accident, Home Accident, Illness)
- Search incidents by ID with client details
- Delete incidents with confirmation
- View incidents by contract
- View incidents by client
- Display incidents in descending order of cost
- Show incidents that occurred before a specific date
- Calculate total incident costs for clients

## 🏗️ Architecture

The application follows a layered MVC (Model-View-Controller) architecture:

### **Models Layer**
- **Abstract Classes**: `Model` (base database operations), `Person` (shared person attributes)
- **Entities**: `Client`, `Advisor`, `Contract`, `Incident`
- **Enums**: `ContractType`, `IncidentType`

### **Services Layer**
- `ClientService`: Business logic for client operations
- `AdvisorService`: Business logic for advisor operations
- `ContractService`: Business logic for contract operations
- `IncidentService`: Business logic for incident operations

### **Views Layer**
- `View`: Abstract base class with input/output utilities
- `ClientView`: Client management interface
- `AdvisorView`: Advisor management interface
- `ContractView`: Contract management interface
- `IncidentView`: Incident management interface

### **Database Layer**
- `DB`: Singleton database connection manager
- PostgreSQL database with connection pooling

## 🗄️ Database Schema

The system uses PostgreSQL with the following main tables:

### **advisors**
```sql
- id (PRIMARY KEY)
- first_name
- last_name
- email
```

### **clients**
```sql
- id (PRIMARY KEY)
- first_name
- last_name
- email
- advisor_id (FOREIGN KEY → advisors.id)
```

### **contracts**
```sql
- id (PRIMARY KEY)
- type (CAR, PROPERTY, HEALTH)
- start_date
- end_date
- client_id (FOREIGN KEY → clients.id)
```

### **incidents**
```sql
- id (PRIMARY KEY)
- type (CAR_ACCIDENT, HOME_ACCIDENT, ILLNESS)
- date
- description
- cost
- contract_id (FOREIGN KEY → contracts.id)
```

## 🚀 Installation

### Prerequisites
- Java 8 or higher
- PostgreSQL database
- JDBC PostgreSQL driver

### Database Setup
1. Install and start PostgreSQL
2. Create a database named `mydb`
3. Update database credentials in `DB.java`:
   ```java
   String url = "jdbc:postgresql://localhost:5433/mydb";
   String user = "myuser";
   String password = "mypassword";
   ```

### Running the Application
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd JavaAssurance
   ```

2. Compile the Java files:
   ```bash
   javac -cp ".:path/to/postgresql-driver.jar" src/**/*.java
   ```

3. Run the application:
   ```bash
   java -cp ".:path/to/postgresql-driver.jar:src" Main
   ```

## 💻 Usage

Upon starting the application, you'll see the main menu:

```
=== Welcome to Javassurance ===

=== MAIN MENU ===
1. Manage Clients
2. Manage Contracts
3. Manage Incidents
4. Manage Advisors
5. Exit
```

### Example Workflows

**Adding a New Client:**
1. Select "1. Manage Clients"
2. Choose "1. Create Client"
3. Enter client details and select an advisor

**Reporting an Incident:**
1. Select "3. Manage Incidents"
2. Choose "1. Create Incident"
3. Select incident type, enter details, and choose a contract

**Finding High-Cost Incidents:**
1. Select "1. Manage Clients"
2. Choose "5. Show All Clients that have an incident cost larger than a specific cost"
3. Enter the cost threshold

## 📁 Project Structure

```
JavaAssurance/
├── src/
│   ├── Main.java                     # Application entry point
│   ├── Database/
│   │   └── DB.java                   # Database connection manager
│   ├── Models/
│   │   ├── Model.java                # Abstract base model
│   │   ├── Person.java               # Abstract person class
│   │   ├── Client.java               # Client entity
│   │   ├── Advisor.java              # Advisor entity
│   │   ├── Contract.java             # Contract entity
│   │   └── Incident.java             # Incident entity
│   ├── Services/
│   │   ├── ClientService.java        # Client business logic
│   │   ├── AdvisorService.java       # Advisor business logic
│   │   ├── ContractService.java      # Contract business logic
│   │   └── IncidentService.java      # Incident business logic
│   ├── Views/
│   │   ├── View.java                 # Abstract view class
│   │   ├── ClientView.java           # Client interface
│   │   ├── AdvisorView.java          # Advisor interface
│   │   ├── ContractView.java         # Contract interface
│   │   └── IncidentView.java         # Incident interface
│   └── Enums/
│       ├── ContractType.java         # Contract type enumeration
│       └── IncidentType.java         # Incident type enumeration
└── README.md
```

## 📚 API Reference

### Key Service Methods

#### ClientService
- `create(String firstName, String lastName, String email, Integer advisorId)`
- `findById(int id): Client`
- `deleteById(int id): boolean`
- `getAllOrderedByFamilyName(): List<Client>`
- `getClientsHaveIncidentsLargerThan(double threshold): List<Client>`
- `getClientsByAdvisorId(int advisorId): List<Client>`

#### AdvisorService
- `create(String firstName, String lastName, String email)`
- `findById(int id): Advisor`
- `deleteById(int id): boolean`

#### ContractService
- `create(ContractType type, LocalDate startDate, LocalDate endDate, Integer clientId)`
- `findById(int id): Contract`
- `deleteById(int id): boolean`
- `getAll(): List<Contract>`
- `getContractsByClientId(int clientId): List<Contract>`

#### IncidentService
- `create(IncidentType type, LocalDate date, String description, Integer cost, Integer contractId)`
- `findById(int id): Incident`
- `deleteById(int id): boolean`
- `getAll(): List<Incident>`

### Input Validation
- All numeric inputs are validated
- Date inputs follow YYYY-MM-DD format
- Required fields are checked before database operations
- Confirmation prompts for delete operations

## 🔧 Technical Features

- **Singleton Pattern**: Database connection management
- **Abstract Factory Pattern**: Model and View hierarchies
- **Input Validation**: Comprehensive error handling for user inputs
- **SQL Injection Prevention**: Parameterized queries
- **Date Handling**: Java LocalDate for date operations
- **Optional Handling**: Safe null handling for optional fields
- **Stream API**: Modern Java collection processing

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## 🔮 Future Enhancements

- Web-based interface
- REST API endpoints
- Report generation (PDF/Excel)
- Email notifications
- Audit logging
- Multi-language support
- Dashboard with analytics

---

**Built using Java and PostgreSQL**