package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Person;
import exceptions.PersonNotFoundException;
import exceptions.ValidationErrorException;
import facade.Facade;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mappers.PersonDTO;

@Path("person")
public class PersonResource {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Facade f = new Facade(Persistence.createEntityManagerFactory("jpapu"));

    @Context
    private UriInfo context;

    public PersonResource() {
    }

    /**
     * This method returns all persons.
     * @return all persons in a array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsArray() {
        return Response.ok().entity(gson.toJson(f.getAllPersons())).build();
    }

    /**
     * This method returns the person with the corresponding id.
     * @param id
     * @return getPerson    the person with the parameter id
     * @throws PersonNotFoundException 
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Long id) throws PersonNotFoundException {
        if (f.getPerson(id) == null) {
            throw new PersonNotFoundException("Unable to find a person with the provided ID");
        }
        return Response.ok().entity(gson.toJson(f.getPerson(id))).build();
    }

    /**
     * Method returns all persons with the requested hobby
     * @param hobby
     * @return getPetsonsByHobby all persons with parameter hobby
     * @throws PersonNotFoundException 
     */
    @GET
    @Path("/hobby/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByHobby(@PathParam("hobby") String hobby) throws PersonNotFoundException {
        if (f.getPersonsByHobby(hobby).isEmpty()) {
            throw new PersonNotFoundException("Unable to find any persons with the provided hobby");
        }
        return Response.ok().entity(gson.toJson(f.getPersonsByHobby(hobby))).build();
    }
    /**
     * Method returns all persons with that requested zip-code.
     * @param zipcode
     * @return getPersonByZipcode
     * @throws PersonNotFoundException 
     */
    @GET
    @Path("/zipcode/{zip}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByZipcode(@PathParam("zip") String zipcode) throws PersonNotFoundException {
        if (f.getPersonsByZipcode(zipcode).isEmpty()) {
            throw new PersonNotFoundException("Unable to find any persons with the provided zipcode");
        }
        return Response.ok().entity(gson.toJson(f.getPersonsByZipcode(zipcode))).build();
    }
    
    @GET
    @Path("/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByName(@QueryParam("firstname") String fname, @QueryParam("lastname") String lname) throws PersonNotFoundException {
        if (f.getPersonsByName(fname, lname).isEmpty()) {
            throw new PersonNotFoundException("Unable to find any persons with the provided name");
        }
        return Response.ok().entity(gson.toJson(f.getPersonsByName(fname, lname))).build();
    }
    
    /**
     * Method returns all persons living in the requested city
     * @param city
     * @return all persons living in the same city
     * @throws PersonNotFoundException 
     */
    @GET
    @Path("/city/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonsByCity(@PathParam("city") String city) throws PersonNotFoundException {
        if (f.getPersonsInCity(city).isEmpty()) {
            throw new PersonNotFoundException("Unable to find any persons");
        }
        return Response.ok().entity(gson.toJson(f.getPersonsInCity(city))).build();
    }
    /**
     * Method returns the person with the requested phone number.
     * @param number
     * @return the person with the phone number
     * @throws PersonNotFoundException 
     */
    @GET
    @Path("/phone/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonByPhone(@PathParam("number") int number) throws PersonNotFoundException {
        if (f.getPersonByPhone(number).isEmpty()) {
            throw new PersonNotFoundException("Unable to find a person with the provided phone number");
        }
        return Response.ok().entity(gson.toJson(f.getPersonByPhone(number))).build();
    }
    /**
     * Method creates a new person with the name included.
     * @param content
     * @return ok response.
     * @throws ValidationErrorException 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postPerson(String content) throws ValidationErrorException {
        Person person = gson.fromJson(content, Person.class);
        if (person.getFirstName().equals("") || person.getLastName().equals("")) {
            throw new ValidationErrorException("First Name or Last Name is missing");
        }
        f.createPerson(person);
        return Response.ok().entity(gson.toJson(person)).build();
    }
    /**
     * Method used to update person
     * @param content
     * @return ok response.
     * @throws ValidationErrorException
     * @throws PersonNotFoundException 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(String content) throws ValidationErrorException, PersonNotFoundException {
        PersonDTO person = gson.fromJson(content, PersonDTO.class);
        if (person.getEmail().equals("") || person.getFirstName().equals("") || person.getLastName().equals("")) {
            throw new ValidationErrorException("Email, First Name or Last Name is missing");
        }
        if (f.getPerson(person.getId()) == null) {
            throw new PersonNotFoundException("Unable to delete. No person with the provided ID exists");
        }
        return Response.ok().entity(gson.toJson(f.updatePerson(person))).build();
    }
    /**
     * Method deletes the person with that id.
     * @param id
     * @return ok response
     * @throws PersonNotFoundException 
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id) throws PersonNotFoundException {
        if (f.getPerson(id) == null) {
            throw new PersonNotFoundException("Could not delete. No person with provided id exists");
        }
        return Response.ok().entity(gson.toJson(f.deletePerson(id))).build();
    }

}
