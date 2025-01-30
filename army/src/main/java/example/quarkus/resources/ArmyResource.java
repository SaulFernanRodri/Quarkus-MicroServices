package example.quarkus.resources;

import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Army;
import example.quarkus.services.ArmyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/armies")
public class ArmyResource {

    private final ArmyService armyService;

    @Inject
    public ArmyResource(ArmyService armyService) {
        this.armyService = armyService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArmies() {
        List<ResponseDto> armies = armyService.getArmy();
        if (armies.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(armies).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArmyById(@PathParam("id") Long id) {
        Optional<ResponseDto> army = Optional.ofNullable(armyService.getArmyById(id));
        return army.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArmy(Army army) {
        ResponseDto createdArmy = armyService.createArmy(army);
        return Response.ok(createdArmy).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateArmy(@PathParam("id") Long id, Army army) {
        Optional<ResponseDto> updatedArmy = Optional.ofNullable(armyService.updateArmy(id, army));
        return updatedArmy.map(responseDto -> Response.ok(responseDto).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArmy(@PathParam("id") Long id) {
        armyService.deleteArmy(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/soldiers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArmyWithSoldier(@PathParam("id") Long id) {
        return Response.ok(armyService.getArmyWithSoldier(id)).build();
    }

    @PUT
    @Path("/{id}/soldiers/{soldierId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSoldierToArmy(@PathParam("id") Long id, @PathParam("soldierId") Long soldierId) {
        return Response.ok(armyService.addSoldierToArmy(id, soldierId)).build();
    }

    @DELETE
    @Path("/{id}/soldiers/{soldierId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSoldierFromArmy(@PathParam("id") Long id, @PathParam("soldierId") Long soldierId) {
        armyService.removeSoldierFromArmy(id, soldierId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/inventory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArmyWithInventory(@PathParam("id") Long id) {
        return Response.ok(armyService.getArmyWithInventory(id)).build();
    }

}
