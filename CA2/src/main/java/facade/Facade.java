package facade;

import entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import mappers.CityInfoDTO;
import mappers.PersonDTO;

public class Facade {
    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // Adds a Person to the database
    public PersonDTO createPerson(Person person){
        PersonDTO p = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p WHERE p.id = :id");
            dQuery.setParameter("id", person.getId());
            p = (PersonDTO) dQuery.getSingleResult();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return p;
    }
    
    // Returns a Person from a given id
    public PersonDTO getPerson(Long id){
        PersonDTO p = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p WHERE p.id = :id");
            dQuery.setParameter("id", id);
            p = (PersonDTO) dQuery.getSingleResult();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return p;
    }
    
    // Returns a Person from a given phone number
    public List<PersonDTO> getPersonByPhone(int phone){
        List<PersonDTO> p = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(person) FROM Phone phone JOIN phone.person person WHERE phone.number = :phone");
            dQuery.setParameter("phone", phone);
            p = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return p;
    }
    
    // Returns a list of Persons from a given hobby
    public List<PersonDTO> getPersonsByHobby(String hobby){
        List<PersonDTO> persons = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p JOIN p.hobbies h WHERE h.name = :hobby");
            dQuery.setParameter("hobby", hobby);
            persons = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return persons;
    }
    
    // Returns a list of all the cities in Denmark
    public List<String> getZipCodes(){
        List<String> zipCodes = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT ci.zip FROM CityInfo ci");
            zipCodes = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return zipCodes;
    }
    
    // Returns a list of Persons from a given city
    public List<PersonDTO> getPersonsInCity(String city){
        List<PersonDTO> persons = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p JOIN p.address a JOIN a.cityInfo ci WHERE ci.city = :city");
            dQuery.setParameter("city", city);
            persons = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return persons;
    }
    
    // Returns a list of Persons from a given city
    public List<PersonDTO> getPersonsByZipcode(String zip){
        List<PersonDTO> persons = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p JOIN p.address a JOIN a.cityInfo ci WHERE ci.zip = :zip");
            dQuery.setParameter("zip", zip);
            persons = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return persons;
    }
    
    // Returns a list of Persons from a given name
    public List<PersonDTO> getPersonsByName(String firstName, String lastName){
        List<PersonDTO> persons = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p WHERE p.firstName = :fname AND p.lastName = :lname");
            dQuery.setParameter("fname", firstName);
            dQuery.setParameter("lname", lastName);
            persons = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return persons;
    }
    
    // Updates a Person with a given ID
    public PersonDTO updatePerson(PersonDTO person){
        Person p = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person dbPerson=em.find(Person.class, person.getId());
            dbPerson.setEmail(person.getEmail());
            dbPerson.setFirstName(person.getFirstName());
            dbPerson.setLastName(person.getLastName());
            em.merge(dbPerson);
            em.getTransaction().commit();
            p = em.find(Person.class, person.getId());
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return new PersonDTO(p);
    }
    
    // Deletes a Person with a given ID
    public PersonDTO deletePerson(Long id){
        Person person = null;
        PersonDTO p = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            p = new PersonDTO(person);
            em.remove(person);
            em.getTransaction().commit();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return p;
    }
    
    // Returns a list containing all Persons
    public List<PersonDTO> getAllPersons(){
        List<PersonDTO> persons = null;
        EntityManager em = getEntityManager();
        try {
            Query dQuery = em.createQuery("SELECT NEW mappers.PersonDTO(p) FROM Person p");
            persons = dQuery.getResultList();
        } catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
        return persons;
    }
}
