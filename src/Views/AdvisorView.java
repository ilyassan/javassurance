package Views;

import Models.Advisor;
import Models.Client;
import Services.AdvisorService;
import Services.ClientService;

import java.util.List;

public class AdvisorView extends View {

    public static void showMenu() {
        println("\n=== ADVISORS MANAGEMENT ===");
        println("1. Create Advisor");
        println("2. Show All Clients of an Advisor");
        println("3. Back to Main Menu");
        print("Enter your choice: ");

        int choice = getIntInput();

        switch(choice) {
            case 1:
                print("Enter advisor first name: ");
                String firstName = getStringInput();
                print("Enter advisor last name: ");
                String lastName = getStringInput();
                print("Enter advisor email: ");
                String email = getStringInput();

                AdvisorService.create(firstName, lastName, email);
                println("Advisor created successfully!");

                pauseBeforeMenu();
                break;
            case 2:
                showClientsOfAdvisor();
                pauseBeforeMenu();
                break;
            case 3:
                return;
            default:
                println("Invalid choice");
        }
    }

    public static Advisor selectAdvisor() {
        List<Advisor> advisors = Advisor.getAll();

        if (advisors.isEmpty()) {
            println("No Advisors found. Please create an advisor first.");
            return null;
        }

        println("\n=== SELECT ADVISOR ===");
        for (int i = 1; i <= advisors.size(); i++) {
            Advisor advisor = advisors.get(i - 1);
            System.out.printf(
                    "%d - %s %s\n",
                    i,
                    advisor.getFirstName(),
                    advisor.getLastName()
            );
        }

        print("Select an advisor: ");
        int choice = getIntInput();

        if (choice >= 1 && choice <= advisors.size()) {
            return advisors.get(choice - 1);
        } else {
            println("Invalid selection.");
            return null;
        }
    }

    public static void showClientsOfAdvisor() {
        Advisor advisor = selectAdvisor();

        if (advisor == null) {
            return;
        }

        List<Client> clients = ClientService.getClientsByAdvisorId(advisor.getId());

        if (clients.isEmpty()) {
            println("\nNo clients found for advisor: " + advisor.getFirstName() + " " + advisor.getLastName());
        } else {
            println("\n=== CLIENTS OF ADVISOR: " + advisor.getFirstName() + " " + advisor.getLastName() + " ===");
            clients.forEach(client -> {
                println("--------------------");
                println("ID: " + client.getId());
                println("First Name: " + client.getFirstName());
                println(client.getFamilyName()
                        .map(name -> "Family Name: " + name)
                        .orElse("Family Name: Not provided"));
                println("Email: " + client.getEmail());
            });
            println("--------------------");
            println("Total clients: " + clients.size());
        }
    }
}
