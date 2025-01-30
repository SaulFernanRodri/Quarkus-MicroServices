package example.quarkus.services;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/inventories")
@RegisterRestClient(configKey = "inventory-service-client")
public interface InventoryServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getInventory();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getInventoryById(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createInventory(String inventoryJson);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateInventory(@PathParam("id") String id, String inventoryJson);

    @DELETE
    @Path("/{id}")
    Response deleteInventory(@PathParam("id") String id);

    @GET
    @Path("/army/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getInventoryByArmyId(@PathParam("id") Long armyId);
}
