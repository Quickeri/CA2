package mappers;

import entity.Person;
import java.util.List;

public class ContactInfoDTO {
    
    private String name;
    private String email;
    private List<Integer> phones;

    public ContactInfoDTO(Person person) {
        this.name = person.getFirstName() + " " + person.getLastName();
        this.email = person.getEmail();
//        for(Phone p : person.getPhones())
//        this.phones = phones;
    }
}
