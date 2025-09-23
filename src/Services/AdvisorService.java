package Services;

import Models.Advisor;

public class AdvisorService {

    public static void create(String firstName, String lastName, String email) {
        // validate the client informations
        if(firstName.isEmpty()) {
            System.out.println("First Name cannot be empty");
        }
        if(lastName.isEmpty()) {
            System.out.println("Last Name cannot be empty");
        }
        if(email.isEmpty()) {
            System.out.println("Email cannot be empty");
        }

        Advisor advisor = new Advisor(null, firstName, lastName, email);
        advisor.create();
    }

    public static Advisor findById(int id) {
        return Advisor.find(id);
    }
}
