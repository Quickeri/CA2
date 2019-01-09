package facade;

import entity.Person;
import java.util.List;
import javax.persistence.Persistence;
import mappers.PersonDTO;

public class FacadeTest {
    public static void main(String[] args) {
        Facade f = new Facade(Persistence.createEntityManagerFactory("jpapu"));
        
        // Test create Person
//        System.out.println("Test createPerson(): ");
//        System.out.println(f.createPerson(new Person("someemail", "Egon", "Hansen")));
//        System.out.println(f.createPerson(new Person("randomemail", "Henrik", "Petersen")));       
        
        // Test get Person
        System.out.println("Test getPerson(): ");
        System.out.println(f.getPerson(1L));
        System.out.println(f.getPerson(2L) + "\n");
        
        // Test get Persons by phone
        System.out.println("Test getPersonByPhone(): ");
        List<PersonDTO> persons1 = f.getPersonByPhone(12345678);
        for(PersonDTO p : persons1){
            System.out.println(p);
        }
        System.out.println("");
        
        // Test get person by hobby
        System.out.println("Test getPersonsByHobby(): ");
        List<PersonDTO> persons2 = f.getPersonsByHobby("running");
        for(PersonDTO p : persons2){
            System.out.println(p);
        }
        System.out.println("");
        
        // Test get person by zipcode
        System.out.println("Test getPersonsByZipcode(): ");
        List<PersonDTO> persons3 = f.getPersonsByZipcode("1324");
        for(PersonDTO p : persons3){
            System.out.println(p);
        }
        System.out.println("");
        
        // Test get person in city
        System.out.println("Test getPersonsInCity(): ");
        List<PersonDTO> persons4 = f.getPersonsInCity("København K");
        for(PersonDTO p : persons4){
            System.out.println(p);
        }
        System.out.println("");
        
         // Test get person by name
        System.out.println("Test getPersonsByName(): ");
        List<PersonDTO> persons5 = f.getPersonsByName("Lars", "Larsen");
        for(PersonDTO p : persons5){
            System.out.println(p);
        }
        System.out.println("");
        
        
        // Test update person
//        System.out.println("Test updatePerson(): ");
//        System.out.println(f.updatePerson(new Person (1l, "someemail", "Hans", "Hansen")));
        
        // Test delete person
//        System.out.println("Test deletePerson(): ");
//        System.out.println(f.deletePerson(1L) + "\n");

        // Test get zip codes
//        System.out.println("Test getZipCodes(): ");
//        List<String> zipCodes = f.getZipCodes();
//        for(String s : zipCodes){
//            System.out.println(s);
//        }
//        System.out.println("");
        
        // Test get all Persons
        System.out.println("Test getAllPersons(): ");
        List<PersonDTO> persons = f.getAllPersons();
        for(PersonDTO p : persons){
            System.out.println(p);
        }
        System.out.println("");
    }
}
