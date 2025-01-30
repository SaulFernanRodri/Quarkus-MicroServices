package example.quarkus.resources;

import example.quarkus.services.ArmyServiceClient;
import example.quarkus.services.SoldierServiceClient;
import example.quarkus.services.InventoryServiceClient;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/v1/gateway")
public class GatewayResource {

    private final ArmyServiceClient armyServiceClient;
    private final SoldierServiceClient soldierServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    public GatewayResource(
            @RestClient ArmyServiceClient armyServiceClient,
            @RestClient SoldierServiceClient soldierServiceClient,
            @RestClient InventoryServiceClient inventoryServiceClient) {
        this.armyServiceClient = armyServiceClient;
        this.soldierServiceClient = soldierServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GET
    @Path("/armies")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArmies() {
        return armyServiceClient.getAllArmies();
    }

    @GET
    @Path("/armies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response getArmyById(@PathParam("id") Long id) {
        return armyServiceClient.getArmyById(id);
    }

    @GET
    @Path("/soldiers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSoldiers(@QueryParam("page") @DefaultValue("0") int page) {
        return soldierServiceClient.getAllSoldiers(page);
    }

    @GET
    @Path("/soldiers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSoldiersById(@PathParam("id") Long id) {
        return soldierServiceClient.getSoldierById(id);
    }

    @GET
    @Path("/inventories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInventories() {
        return inventoryServiceClient.getInventory();
    }

    @GET
    @Path("/inventories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryById(@PathParam("id") String id) {
        return inventoryServiceClient.getInventoryById(id);
    }
}
