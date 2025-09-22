package Models;

import Enums.Role;

public class Advisor extends Person {

    Advisor(String firstName, String lastName, String email) {
        super(firstName, lastName, email, Role.ADVISOR);
    }
}