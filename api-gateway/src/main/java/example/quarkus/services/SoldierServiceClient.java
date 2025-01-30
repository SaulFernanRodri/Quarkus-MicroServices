package example.quarkus.services;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/soldiers")
@RegisterRestClient(configKey = "soldier-service-client")
public interface SoldierServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllSoldiers(@QueryParam("page") @DefaultValue("0") int page);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getSoldierById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createSoldier(String requestDtoJson);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateSoldier(@PathParam("id") Long id, String requestDtoJson);

    @DELETE
    @Path("/{id}")
    Response deleteSoldier(@PathParam("id") Long id);

}
