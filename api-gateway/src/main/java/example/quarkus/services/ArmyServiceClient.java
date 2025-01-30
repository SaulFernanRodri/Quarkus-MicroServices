package example.quarkus.services;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/armies")
@RegisterRestClient(configKey = "army-service-client")
public interface ArmyServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllArmies();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArmyById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createArmy(String armyJson);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateArmy(@PathParam("id") Long id, String armyJson);

    @DELETE
    @Path("/{id}")
    Response deleteArmy(@PathParam("id") Long id);

    @GET
    @Path("/{id}/soldier")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArmyWithSoldier(@PathParam("id") Long id);

    @PUT
    @Path("/{id}/soldiers/{soldierId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response addSoldierToArmy(@PathParam("id") Long id, @PathParam("soldierId") Long soldierId);

    @DELETE
    @Path("/{id}/soldiers/{soldierId}")
    Response removeSoldierFromArmy(@PathParam("id") Long id, @PathParam("soldierId") Long soldierId);

    @GET
    @Path("/{id}/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    Response getArmyWithInventory(@PathParam("id") Long id);
}
