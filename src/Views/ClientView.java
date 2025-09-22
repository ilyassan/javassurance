package Views;

import Models.Advisor;
import Models.Client;
import Services.ClientService;

public class ClientView extends View {

    public static void showMenu() {
        println("\n=== CLIENTS MANAGEMENT ===");
        println("1. Create Client");
        println("2. Search Client by ID");
        println("3. Back to Main Menu");
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
                        println("Name: " + client.getFirstName() + " " + client.getLastName());
                        println("Email: " + client.getEmail());
                        println("Advisor ID: " + client.getAdvisorId());
                    } else {
                        println("Client with ID " + clientId + " not found.");
                    }
                }

                pauseBeforeMenu();
                break;
            case 3:
                return;
            default:
                println("Invalid choice");
        }
    }
}
