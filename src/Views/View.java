package Views;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class View {
    private static final Scanner scanner = new Scanner(System.in);

    public static void println(String string){
        System.out.println(string);
    }

    public static void print(String string){
        System.out.print(string);
    }

    // get string method
    public static String getStringInput() {
        try {
            return scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a whole number.");
            return null;
        }
    }

    public static int getIntInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a whole number.");
            return -1;
        }
    }

    public static double getDoubleInput() {
        try {
            double input = scanner.nextDouble();
            scanner.nextLine();
            return input;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static void pauseBeforeMenu() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
