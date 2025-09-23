package Services;

import Models.Client;
import Models.Contract;
import Models.Incident;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {

    public static void create(String firstName, String lastName, String email, Integer advisorId) {
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
        if(advisorId == null) {
            System.out.println("AdvisorId cannot be empty");
        }

        Client client = new Client(null, firstName, lastName, email, advisorId);
        client.create();
    }

    public static Client findById(int id) {
        return Client.find(id);
    }

    public static boolean deleteById(int id) {
        Client client = Client.find(id);
        if (client != null) {
            return Client.delete(id);
        } else {
            System.out.println("Client with ID " + id + " not found.");
            return false;
        }
    }

    public static List<Client> getAllOrderedByFamilyName() {
        return Client.getAll()
                .stream()
                .sorted(Comparator.comparing(client ->
                    client.getFamilyName().orElse("ZZZ")))
                .collect(Collectors.toList());
    }

    public static List<Client> getClientsHaveIncidentsLargerThan(double threshold) {
        List<Incident> incidents = IncidentService.getAll();
        List<Contract> contracts = ContractService.getAll();

        return getAllOrderedByFamilyName().stream()
                .filter(client -> {
                    List<Integer> clientContractIds = contracts.stream()
                            .filter(contract -> contract.getClientId().equals(client.getId()))
                            .map(Contract::getId)
                            .collect(Collectors.toList());

                    return incidents.stream().anyMatch(incident ->
                            clientContractIds.contains(incident.getContractId()) &&
                                    incident.getCost() >= threshold);
                })
                .collect(Collectors.toList());
    }

    public static List<Client> getClientsByAdvisorId(int advisorId) {
        return getAllOrderedByFamilyName().stream()
                .filter(client -> client.getAdvisorId().equals(advisorId))
                .collect(Collectors.toList());
    }

}
