package Services;

import Enums.IncidentType;
import Models.Incident;

import java.time.LocalDate;

public class IncidentService {
    public static void create(IncidentType type, LocalDate date, String description, Integer cost, Integer contractId) {
        // validate the incident information
        if(type == null) {
            System.out.println("Contract type cannot be empty");
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
        incident.create();
    }

}
