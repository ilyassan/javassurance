package Models;

import java.util.Optional;

public class Client extends Person {
    public Integer advisorId;

    public Client(Integer id, String firstName, String lastName, String email, Integer advisorId) {
        super(id, firstName, lastName, email);
        this.advisorId = advisorId;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public Optional<String> getFamilyName() {
        return Optional.ofNullable(this.lastName);
    }
}