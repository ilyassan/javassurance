package Views;

import Models.Advisor;

import java.util.List;

public class AdvisorView extends View {

    public static Advisor selectAdvisor() {
        List<Advisor> advisors = Advisor.getAll();

        if (advisors.isEmpty()) {
            println("No Advisors found. Please create an advisor first.");
            return null;
        }

        println("\n=== SELECT ADVISOR ===");
        for (int i = 1; i <= advisors.size(); i++) {
            Advisor advisor = advisors.get(i);
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
