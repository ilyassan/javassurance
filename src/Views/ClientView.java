package Views;

import Models.Advisor;
import Models.Client;
import Services.ClientService;
import java.util.List;

public class ClientView extends View {

    public static void showMenu() {
        println("\n=== CLIENTS MANAGEMENT ===");
        println("1. Create Client");
        println("2. Search Client by ID");
        println("3. Delete Client by ID");
        println("4. Show All Clients (Ordered by Family Name)");
        println("5. Show All Clients that have an incident cost larger than a specific cost");
        println("6. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                print("Enter client first name: ");
                String firstName = getStringInput();
                print("Enter client last name: ");
                String lastName = getStringInput();
                print("Enter client email: ");
                String email = getStringInput();

                Advisor advisor = AdvisorView.selectAdvisor();

                if(advisor != null) {
                    ClientService.create(firstName, lastName, email, advisor.id);
                    println("Client created successfully!");
                }

                pauseBeforeMenu();
                break;
            case 2:
                print("Enter client ID: ");
                int clientId = getIntInput();

                if(clientId != -1) {
                    Client client = ClientService.findById(clientId);

                    if(client != null) {
                        println("\n=== CLIENT FOUND ===");
                        println("ID: " + client.getId());
                        println("First Name: " + client.getFirstName());
                        println(
                                client.getFamilyName()
                                        .map(name -> "Family Name: " + name)
                                        .orElse("Family Name: Not provided")
                        );
                        println("Email: " + client.getEmail());
                        println("Advisor ID: " + client.getAdvisorId());
                    } else {
                        println("Client with ID " + clientId + " not found.");
                    }
                }

                pauseBeforeMenu();
                break;
            case 3:
                print("Enter client ID to delete: ");
                int deleteClientId = getIntInput();

                if(deleteClientId != -1) {
                    print("Are you sure you want to delete this client? (y/N): ");
                    String confirmation = getStringInput();

                    if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                        boolean deleted = ClientService.deleteById(deleteClientId);

                        if(deleted) {
                            println("Client deleted successfully!");
                        } else {
                            println("Failed to delete client or client not found.");
                        }
                    } else {
                        println("Deletion cancelled.");
                    }
                }

                pauseBeforeMenu();
                break;
            case 4:
                List<Client> clients = ClientService.getAllOrderedByFamilyName();

                if(clients.isEmpty()) {
                    println("No clients found.");
                } else {
                    println("\n=== ALL CLIENTS (Ordered by Family Name) ===");
                    clients.stream()
                            .forEach(client -> {
                                println("--------------------");
                                println("ID: " + client.getId());
                                println("First Name: " + client.getFirstName());
                                println(client.getFamilyName()
                                        .map(name -> "Family Name: " + name)
                                        .orElse("Family Name: Not provided"));
                                println("Email: " + client.getEmail());
                                println("Advisor ID: " + client.getAdvisorId());
                            });
                    println("--------------------");
                    println("Total clients: " + clients.size());
                }

                pauseBeforeMenu();
                break;
            case 5:
                print("Enter the cost treshold: ");
                int cost = getIntInput();

                println("\n=== ALL CLIENTS ===");
                ClientService.getClientsHaveIncidentsLargerThan(cost).stream()
                        .forEach(client -> {
                            println("--------------------");
                            println("ID: " + client.getId());
                            println("First Name: " + client.getFirstName());
                            println(client.getFamilyName()
                                    .map(name -> "Family Name: " + name)
                                    .orElse("Family Name: Not provided"));
                            println("Email: " + client.getEmail());
                            println("Advisor ID: " + client.getAdvisorId());
                        });
                println("--------------------");

                pauseBeforeMenu();
                return;
            case 6:
                return;
            default:
                println("Invalid choice");
        }
    }
}
