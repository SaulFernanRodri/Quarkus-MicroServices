package example.quarkus.resources;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.services.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/inventories")
public class InventoryResource {


    InventoryService inventoryService;

    @Inject
    public InventoryResource(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventory() {
        Optional<List<ResponseDto>> inventories = inventoryService.getAllInventories();
        if (inventories.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(inventories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryById(@PathParam("id") String id) {
        ObjectId objectId = getObjectId(id);
        Optional<ResponseDto> inventory = inventoryService.getInventoryById(objectId);
        return inventory.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createInventory(RequestDto inventory) {
        return Response.status(Response.Status.CREATED).entity(inventoryService.addInventory(inventory)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateInventory(@PathParam("id") String id, RequestDto inventory) {
        ObjectId objectId = getObjectId(id);
        Optional<ResponseDto> updatedInventory = inventoryService.updateInventory(objectId, inventory);

        return updatedInventory.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInventory(@PathParam("id") String id) {
        ObjectId objectId = getObjectId(id);
        inventoryService.deleteInventory(objectId);
        return Response.ok().build();
    }

    @GET
    @Path("/army/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventoryByArmyId(@PathParam("id") Long armyId) {
        Optional<ResponseDto> inventory = inventoryService.getInventoryByArmyId(armyId);
        return inventory.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    private ObjectId getObjectId(String id) {
        if (!ObjectId.isValid(id)) {
            throw new WebApplicationException("Invalid ObjectId format", Response.Status.BAD_REQUEST);
        }
        return new ObjectId(id);
    }
}
