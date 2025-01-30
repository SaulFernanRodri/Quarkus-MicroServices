package example.quarkus.services;

import example.quarkus.dtos.InventoryDto;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/api/v1/inventories")
@RegisterRestClient(configKey = "InventoryServiceClient")
public interface InventoryServiceClient {

    @GET
    @Path("/army/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    InventoryDto getInventoryByArmyId(@PathParam("id") Long armyId);
}
