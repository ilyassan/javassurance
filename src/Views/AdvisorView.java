package Views;

import Models.Advisor;
import Services.AdvisorService;

import java.util.List;

public class AdvisorView extends View {

    public static void showMenu() {
        println("\n=== ADVISORS MANAGEMENT ===");
        println("1. Create Advisor");
        println("2. Back to Main Menu");
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
            case 2:
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
}
