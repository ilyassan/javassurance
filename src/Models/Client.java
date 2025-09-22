package Models;

import Enums.Role;

public class Client extends Person {
    public Integer advisorId;

    Client(String firstName, String lastName, String email, Integer advisorId) {
        super(firstName, lastName, email, Role.CLIENT);
        this.advisorId = advisorId;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }
}