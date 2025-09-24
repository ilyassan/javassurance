package Services;

import DAO.IncidentDAO;
import Enums.IncidentType;
import Models.Contract;
import Models.Incident;

import java.time.LocalDate;
import java.util.List;

public class IncidentService {
    private static final IncidentDAO incidentDAO = new IncidentDAO();

    public static void create(IncidentType type, LocalDate date, String description, double cost, Integer contractId) {
        // validate the incident information
        if(type == null) {
            System.out.println("Incident type cannot be empty");
            return;
        }
        if(date == null) {
            System.out.println("date cannot be empty");
            return;
        }
        if(cost <= 0){
            System.out.println("cost cannot be negative");
            return;
        }

        Incident incident = new Incident(null, type, date, description, cost, contractId);
        Integer generatedId = incidentDAO.save(incident);
        incident.id = generatedId;
    }

    public static Incident findById(int id) {
        return incidentDAO.findById(id);
    }

    public static boolean deleteById(int id) {
        Incident incident = incidentDAO.findById(id);
        if (incident != null) {
            return incidentDAO.deleteById(id);
        } else {
            System.out.println("Incident with ID " + id + " not found.");
            return false;
        }
    }

    public static List<Incident> getAll() {
        return incidentDAO.findAll();
    }
}
