package Views;

import Enums.IncidentType;
import Models.Client;
import Models.Contract;
import Models.Incident;
import Services.ClientService;
import Services.ContractService;
import Services.IncidentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IncidentView extends View {

    public static void showMenu() {
        println("\n=== Incident MANAGEMENT ===");
        println("1. Create Incident");
        println("2. Search Incident by ID");
        println("3. Delete Incident by ID");
        println("4. Total cost of Incidents of a client");
        println("5. Show incidents of a specific client");
        println("6. Show incidents of a specific contract");
        println("7. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                createIncident();
                pauseBeforeMenu();
                break;
            case 2:
                searchIncident();
                pauseBeforeMenu();
                break;
            case 3:
                deleteIncident();
                pauseBeforeMenu();
                break;
            case 4:
                incidentsTotalCostOfClient();
                pauseBeforeMenu();
                break;
            case 5:
                showIncidentsOfClient();
                pauseBeforeMenu();
                break;
            case 6:
                showIncidentsOfContract();
                pauseBeforeMenu();
                break;
            case 7:
                return;
            default:
                println("Invalid choice");
        }
    }

    private static void createIncident() {
        // Select incident type
        IncidentType incidentType = selectIncidentType();
        if (incidentType == null) {
            return;
        }

        // Get start date
        print("Enter date (yyyy-MM-dd): ");
        String enteredDate = getStringInput();
        LocalDate date = parseDate(enteredDate);
        if (date == null) {
            println("Invalid date format. Please use yyyy-MM-dd");
            return;
        }

        print("Enter cost: ");
        Integer cost = getIntInput();
        if (cost <= 0) {
            println("Invalid cost.");
            return;
        }

        print("Enter a description (optional): ");
        String description = getStringInput();

        // Select contract
        Contract contract = selectContract();
        if (contract != null) {
            IncidentService.create(incidentType, date, description, cost, contract.getId());
            println("Incident created successfully!");
        }
    }

    private static IncidentType selectIncidentType() {
        println("\n=== SELECT INCIDENT TYPE ===");
        IncidentType[] types = IncidentType.values();
        for (int i = 0; i < types.length; i++) {
            println((i + 1) + ". " + types[i].name());
        }
        print("Select a incident type: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= types.length) {
            return types[choice - 1];
        } else {
            println("Invalid selection.");
            return null;
        }
    }

    private static Contract selectContract() {
        List<Contract> contracts = Contract.getAll();
        List<Client> clients = Client.getAll();

        if (contracts.isEmpty()) {
            println("No contracts found. Please create a contract first.");
            return null;
        }

        println("\n=== SELECT CONTRACT ===");
        for (int i = 1; i <= contracts.size(); i++) {
            Contract contract = contracts.get(i - 1);
            Client client = clients.stream()
                            .filter(cl -> Objects.equals(cl.getId(), contract.getClientId()))
                            .findFirst().orElse(null);
            System.out.printf(
                    "%d - %s | %s %s | Client: %s %s\n",
                    i,
                    contract.getType(),
                    contract.getStartDate(),
                    contract.getEndDate(),
                    client.getFirstName(),
                    client.getLastName()
            );
        }

        print("Select a contract: ");
        int choice = getIntInput();

        if (choice >= 1 && choice <= contracts.size()) {
            return contracts.get(choice - 1);
        } else {
            println("Invalid selection.");
            return null;
        }
    }

    private static void searchIncident() {
        print("Enter incident ID: ");
        int incidentId = getIntInput();

        if(incidentId != -1) {
            Incident incident = IncidentService.findById(incidentId);

            if(incident != null) {
                println("\n=== INCIDENT FOUND ===");
                println("ID: " + incident.getId());
                println("Type: " + incident.getType().name());
                println("Date: " + incident.getDate());
                println("Description: " + incident.getDescription());
                println("Cost: " + incident.getCost());

                // Also show client details
                Contract contract = ContractService.findById(incident.getContractId());
                Client client = ClientService.findById(contract.getClientId());
                if(client != null) {
                    println("\n=== CLIENT DETAILS ===");
                    println("Client Name: " + client.getFirstName() + " " + client.getFamilyName().orElse("[No family name]"));
                    println("Client Email: " + client.getEmail());
                }
            } else {
                println("Incident with ID " + incidentId + " not found.");
            }
        }
    }

    private static void deleteIncident() {
        print("Enter incident ID to delete: ");
        int deleteIncidentId = getIntInput();

        // First show the incident details for confirmation
        Incident incident = IncidentService.findById(deleteIncidentId);

        if(incident != null) {
            println("\n=== INCIDENT TO DELETE ===");
            println("ID: " + incident.getId());
            println("Type: " + incident.getType().name());
            println("Date: " + incident.getDate());
            println("Description: " + incident.getDescription());
            println("Cost: " + incident.getCost());

            // Show client details
            Contract contract = ContractService.findById(incident.getContractId());
            Client client = ClientService.findById(contract.getClientId());

            if(client != null) {
                println("Client: " + client.getFirstName() + " " + client.getFamilyName().orElse("[No family name]"));
            }

            print("\nAre you sure you want to delete this contract? (y/N): ");
            String confirmation = getStringInput();

            if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                boolean deleted = IncidentService.deleteById(deleteIncidentId);

                if(deleted) {
                    println("Contract deleted successfully!");
                } else {
                    println("Failed to delete contract.");
                }
            } else {
                println("Deletion cancelled.");
            }
        } else {
            println("Contract with ID " + deleteIncidentId + " not found.");
        }
    }

    private static void incidentsTotalCostOfClient() {
        Client client = selectClient();
        List<Integer> contractIds = ContractService.getContractsByClientId(client.getId())
                                    .stream().map(contract -> contract.getId())
                                    .collect(Collectors.toList());

        double totalCost = IncidentService.getAll().stream()
                .filter(incident -> contractIds.contains(incident.getContractId()))
                .mapToDouble(incident -> incident.cost)
                .sum();

        print("Total cost: " + totalCost);
    }

    public static void showIncidentsOfContract() {
        Contract contract = selectContract();

        println("\n================");
        IncidentService.getAll().stream()
                .filter(incident -> incident.getContractId().equals(contract.getId()))
                .forEach(incident -> {
                    println("ID: " + incident.getId());
                    println("Type: " + incident.getType().name());
                    println("Date: " + incident.getDate());
                    println("Description: " + incident.getDescription());
                    println("Cost: " + incident.getCost());
                    println("\n================");
                });
    }

    public static void showIncidentsOfClient() {
        Client client = selectClient();
        List<Integer> contractIds = ContractService.getContractsByClientId(client.getId())
                .stream().map(contract -> contract.getId())
                .collect(Collectors.toList());

        println("\n================");
        IncidentService.getAll().stream()
                .filter(incident -> contractIds.contains(incident.getContractId()))
                .forEach(incident -> {
                    println("ID: " + incident.getId());
                    println("Type: " + incident.getType().name());
                    println("Date: " + incident.getDate());
                    println("Description: " + incident.getDescription());
                    println("Cost: " + incident.getCost());
                    println("\n================");
                });
    }



    private static Client selectClient() {
        List<Client> clients = ClientService.getAllOrderedByFamilyName();

        if (clients.isEmpty()) {
            println("No clients found. Please create a client first.");
            return null;
        }

        println("\n=== SELECT CLIENT ===");
        for (int i = 1; i <= clients.size(); i++) {
            Client client = clients.get(i - 1);
            System.out.printf(
                    "%d - %s %s (ID: %d)\n",
                    i,
                    client.getFirstName(),
                    client.getFamilyName().orElse("[No family name]"),
                    client.getId()
            );
        }

        print("Select a client: ");
        int choice = getIntInput();

        if (choice >= 1 && choice <= clients.size()) {
            return clients.get(choice - 1);
        } else {
            println("Invalid selection.");
            return null;
        }
    }

}