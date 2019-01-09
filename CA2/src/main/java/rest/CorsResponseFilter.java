package rest;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsResponseFilter implements ContainerResponseFilter
{
    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext res) throws IOException
    {        
        if(res.getHeaders().get("Access-Control-Allow-Origin") == null)
        {
            System.out.println("RESPONSE HEADER ADDED...");
            res.getHeaders().add("Access-Control-Allow-Origin", "*");
            res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            res.getHeaders().add("Access-Control-Allow-Credentials", "true");
            res.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type");
        }
    }
}