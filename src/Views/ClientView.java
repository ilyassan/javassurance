package Views;

import Models.Advisor;
import Models.Client;
import Services.ClientService;

public class ClientView extends View {

    public static void showMenu() {
        println("\n=== CLIENTS MANAGEMENT ===");
        println("1. Create Client");
        println("2. Search Client by ID");
        println("3. Delete Client by ID");
        println("4. Back to Main Menu");
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
                return;
            default:
                println("Invalid choice");
        }
    }
}
