package example.quarkus.services;

import example.quarkus.models.Soldier;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/v1/soldiers")
@RegisterRestClient(configKey = "SoldierServiceClient")
public interface SoldierServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Soldier> getAllSoldiers();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Soldier getSoldierById(@PathParam("id") Long id);
}
