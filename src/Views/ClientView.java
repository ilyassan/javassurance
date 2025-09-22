package Views;

import Models.Advisor;
import Services.ClientService;

public class ClientView extends View {

    public static void showMenu() {
        println("\n=== CLIENTS MANAGEMENT ===");
        println("1. Create Client");
        println("2. Back to Main Menu");
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
            case 2:
                return;
            default:
                println("Invalid choice");
        }
    }
}
