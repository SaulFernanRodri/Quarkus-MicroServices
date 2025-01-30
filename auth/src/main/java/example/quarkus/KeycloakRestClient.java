package example.quarkus;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "keycloak-client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface KeycloakRestClient {

    @POST
    @Path("/realms/{realm}/protocol/openid-connect/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    String getToken(@PathParam("realm") String realm, Form form);

    @POST
    @Path("/admin/realms/{realm}/users")
    String createUser(@PathParam("realm") String realm, @HeaderParam("Authorization") String adminToken, UserDto user);

    @GET
    @Path("/admin/realms/{realm}/roles/{roleName}")
    String getRole(@PathParam("realm") String realm, @HeaderParam("Authorization") String adminToken, @PathParam("roleName") String roleName);

    @POST
    @Path("/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    void assignRole(@PathParam("realm") String realm, @PathParam("userId") String userId, @HeaderParam("Authorization") String adminToken, List<String> roles);
}
