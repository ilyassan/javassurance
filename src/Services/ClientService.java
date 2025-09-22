package Services;

import Models.Client;

public class ClientService {

    public static void create(String firstName, String lastName, String email, String advisorId) {
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
        if(advisorId.isEmpty()) {
            System.out.println("AdvisorId cannot be empty");
        }

        Client client = new Client(null, firstName, lastName, email, advisorId);
        client.create();
    }
}
