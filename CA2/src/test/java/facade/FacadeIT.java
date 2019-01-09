
// BEWARE!
// testpu must be initialized with testDB.sql in persistence.xml
// <property name="javax.persistence.sql-load-script-source" value="Scripts/TestDB.sql"/>

package facade;

import entity.Person;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import mappers.PersonDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class FacadeIT {
    
    private Facade f;
    
    public FacadeIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // testpu must be initialized with testDB.sql in persistence.xml
        // <property name="javax.persistence.sql-load-script-source" value="Scripts/TestDB.sql"/>
        Persistence.generateSchema("testpu", null);
        f = new Facade(Persistence.createEntityManagerFactory("testpu"));
        System.out.println("setup");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class Facade.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        Facade instance = f;
        EntityManager result = instance.getEntityManager();
        assertTrue(result instanceof EntityManager);
    }

    /**
     * Test of createPerson method, of class Facade.
     */
    @Test
    public void testCreatePerson() {
        System.out.println("createPerson");
        // the first available id in test database.
        long id = 14;
        Person person = new Person(id, "jan@jan.com", "Jan", "Jansen");
        Facade instance = f;
        PersonDTO p = new PersonDTO(person);
        instance.createPerson(person);
        PersonDTO expResult = p;
        PersonDTO result = instance.getPerson(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPerson method, of class Facade. 2 test cases, one where the person with the given id exists, and one where the person doesn't exist.
     */
    @Test
    public void testGetPerson() {
        System.out.println("getPerson");
        Long id = new Long(1);
        Facade instance = f;
        PersonDTO expResult = new PersonDTO(new Person(id, "Lars@Somewhere.com", "Lars", "Larsen"));
        PersonDTO result = instance.getPerson(id);
        // expresult and result are differnt objects although content is the same.
        assertEquals(expResult, result);
    }

    /**
     * Test of getPersonByPhone method, of class Facade. 3 test cases, one where the person with the given phone number exists, 
     * one where there is no person with that phone number, and one where the phone number doesn't exist.
     */
    @Test
    public void testGetPersonByPhone() {
        System.out.println("getPersonByPhone");
        int phone = 56565363;
        Facade instance = f;
        Long expResult = new Long(4);
        List<PersonDTO> result = (List<PersonDTO>) instance.getPersonByPhone(phone);
        assertEquals(expResult, result.get(0).getId());
    }

    /**
     * Test of getPersonsByHobby method, of class Facade. 3 test cases, one where the persons with the given hobby are found,
     * one where there are no persons found with that hobby, and one where the hobby doesn't exist.
     */
    @Test
    public void testGetPersonsByHobby() {
        System.out.println("getPersonsByHobby");
        String hobby = "Yoga";
        Facade instance = f;
        List<PersonDTO> result = instance.getPersonsByHobby(hobby);
        assertEquals(2, result.size());
        boolean found2=false;
        boolean found8=false;
        for(PersonDTO person : result){
            if(person.getId()==2) {
                found2=true;
            }
            if(person.getId()==8) {
                found8=true;
            }
        }
        assertTrue(found2 && found8);
    }

    /**
     * Test of getZipCodes method, of class Facade.
     */
    @Test
    public void testGetZipCodes() {
        System.out.println("getZipCodes");
        Facade instance = f;
        int count = 1352;
        List<String> result = instance.getZipCodes();
        assertEquals(count, result.size());
        boolean found=false;
        for(String s : result){
            if(s.equals("4500")) {
                found=true;
            }
        }
        assertTrue(found);        
    }

    /**
     * Test of getPersonsInCity method, of class Facade. 3 test cases, one where the persons are found for the given city, 
     * one where the city has no persons, and one where the city doesn't exist
     */
    @Test
    public void testGetPersonsInCity() {
        System.out.println("getPersonsInCity");
        String city = "Frederiksberg C";
        Facade instance = f;
        List<PersonDTO> result = instance.getPersonsInCity(city);
        assertEquals(3, result.size());
        boolean found3=false;
        boolean found4=false;
        boolean found6=false;        
        for(PersonDTO person : result){
            if(person.getId()==3) {
                found3=true;
            }
            if(person.getId()==4) {
                found4=true;
            }
            if(person.getId()==6){
                found6=true;
            }
        }
        assertTrue(found3 && found4 && found6);
    }

    /**
     * Test of updatePerson method, of class Facade. 2 test cases, one where the person gets updated, and one where the person doesn't exist.
     */
    @Test
    public void testUpdatePerson() {
        System.out.println("updatePerson");
        PersonDTO person = new PersonDTO(new Long(4),"ulrik@ulrik.com","Ulrik","Ulriksen");
        Facade instance = f;
        PersonDTO expResult = person;
        PersonDTO result = instance.updatePerson(person);
        assertEquals(expResult, result);
        PersonDTO resultDTO = instance.getPerson(new Long(4));
        assertEquals(person,resultDTO);
    }

    /**
     * Test of deletePerson method, of class Facade. We have 2 cases, one where the person is deleted, and one where the person doesn't exist.
     */
    @Test
    public void testDeletePerson() {
        System.out.println("deletePerson");
        Long id = new Long(13);
        Facade instance = f;
        // Test that the deleted person is returned
        PersonDTO result = instance.deletePerson(id);
        assertEquals(id, result.getId());
        // Test that the deleted person has disappeared
        result = instance.getPerson(id);
        assertEquals(null,result);
    }

    /**
     * Test of getAllPersons method, of class Facade.
     */
    @Test
    public void testGetAllPersons() {
        System.out.println("getAllPersons");
        Facade instance = f;
        List<PersonDTO> result = instance.getAllPersons();
        assertTrue(result.size() > 12);
        assertTrue(result.get(0).getId() != result.get(1).getId());
    }

    /**
     * Test of getPersonsByZipcode method, of class Facade.
     */
    @Test
    public void testGetPersonsByZipcode() {
        System.out.println("getPersonsByZipcode");
        String zip = "1971";
        Facade instance = f;
        List<PersonDTO> expResult = null;
        List<PersonDTO> result = instance.getPersonsByZipcode(zip);
        assertTrue(result.size() == 2);
        boolean found4=false;
        boolean found6=false;        
        for(PersonDTO person : result){
            if(person.getId()==4) {
                found4=true;
            }
            if(person.getId()==6){
                found6=true;
            }
        }
        assertTrue(found4 && found6);
    }

    /**
     * Test of getPersonsByName method, of class Facade.
     */
    @Test
    public void testGetPersonsByName() {
        System.out.println("getPersonsByName");
        String firstName = "Niels";
        String lastName = "Frederiksen";
        Facade instance = f;
        List<PersonDTO> expResult = null;
        List<PersonDTO> result = instance.getPersonsByName(firstName, lastName);
        assertTrue(result.size() == 2);
        boolean found2=false;
        boolean found3=false;        
        for(PersonDTO person : result){
            if(person.getId()==2) {
                found2=true;
            }
            if(person.getId()==3){
                found3=true;
            }
        }
        assertTrue(found2 && found3);
    }
    
}
