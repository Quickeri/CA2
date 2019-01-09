package rest;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsRequestFilter implements ContainerRequestFilter
{
    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException
    {
        if (requestCtx.getRequest().getMethod().equals("OPTIONS"))
        {
            System.out.println("REQUEST WITH METHOD OPTIONS DETECTED...");
            
            requestCtx.abortWith(Response.status(Response.Status.OK).build());
        }
    }
}