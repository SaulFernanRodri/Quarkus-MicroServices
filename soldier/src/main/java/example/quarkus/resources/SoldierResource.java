package example.quarkus.resources;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.services.SoldierService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

@Path("/api/v1/soldiers")
public class SoldierResource {

    private final SoldierService soldierService;

    @Inject
    public SoldierResource(SoldierService soldierService) {
        this.soldierService = soldierService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSoldiers(@QueryParam("page") @DefaultValue("0") int page) {
        List<ResponseDto> soldiers = soldierService.getAllSoldiers(page);
        if (soldiers.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(soldiers).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSoldierById(@PathParam("id") Long id) {
        Optional<ResponseDto> soldier = soldierService.getSoldierById(id);
        return soldier.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSoldier(RequestDto requestDto) {
        ResponseDto createdSoldier = soldierService.addSoldier(requestDto);
        return Response.status(Response.Status.CREATED).entity(createdSoldier).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSoldier(@PathParam("id") Long id) {
        boolean deleted = soldierService.deleteSoldier(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Soldier not found").build();
    }
}
