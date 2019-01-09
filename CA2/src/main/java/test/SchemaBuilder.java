
package test;

import java.util.HashMap;
import javax.persistence.Persistence;

//this class is responsable for populating the database with information.

public class SchemaBuilder {
    public static void main(String[] args) {
        HashMap<String, Object> properties = new HashMap<>();
        //This clears the database by droping the schema and create a new one, which is empty and ready for population with data.
        properties.put("javax.persistence.sql-load-script-source", "Scripts/ClearDB.sql");
        Persistence.generateSchema("jpapu", properties);
        
        
        //This runs the script that fills the database with data. 
        properties.remove("javax.persistence.sql-load-script-source");
        properties.put("javax.persistence.sql-load-script-source", "Scripts/PopulateSQL.sql");
        Persistence.generateSchema("jpapu", properties);     
    }
}
