import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("=== Welcome to Javassurance ===");

        while(true){
            showMainMenu();
            int choice = getIntInput();

            switch (choice){
                // Clients
                case 1: break;
                // Contracts
                case 2: break;
                // Incidents
                case 3: break;
                // Advisor
                case 4: break;
                // Exit
                case 5:
                    System.out.println("Thank you for using Javassurance!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");

            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Manage Clients");
        System.out.println("2. Manage Contracts");
        System.out.println("3. Manage Incidents");
        System.out.println("4. Manage Advisors");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    public static int getIntInput(){
        try {
            int input = scanner.nextInt();
            scanner.nextLine();

            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input.");
            return -1;
        }
    }
}
