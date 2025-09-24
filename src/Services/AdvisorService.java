package Services;

import DAO.AdvisorDAO;
import Models.Advisor;
import java.util.List;

public class AdvisorService {
    private static final AdvisorDAO advisorDAO = new AdvisorDAO();

    public static void create(String firstName, String lastName, String email) {
        // validate the client informations
        if(firstName.isEmpty()) {
            System.out.println("First Name cannot be empty");
            return;
        }
        if(lastName.isEmpty()) {
            System.out.println("Last Name cannot be empty");
            return;
        }
        if(email.isEmpty()) {
            System.out.println("Email cannot be empty");
            return;
        }

        Advisor advisor = new Advisor(null, firstName, lastName, email);
        Integer generatedId = advisorDAO.save(advisor);
        advisor.id = generatedId;
    }

    public static Advisor findById(int id) {
        return advisorDAO.findById(id);
    }

    public static boolean deleteById(int id) {
        Advisor advisor = advisorDAO.findById(id);
        if (advisor != null) {
            return advisorDAO.deleteById(id);
        } else {
            System.out.println("Advisor with ID " + id + " not found.");
            return false;
        }
    }

    public static List<Advisor> getAll() {
        return advisorDAO.findAll();
    }
}
