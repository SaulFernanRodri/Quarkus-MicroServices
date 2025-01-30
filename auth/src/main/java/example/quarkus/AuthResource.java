package example.quarkus;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;

@Path("api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    KeycloakService keycloakService;

    @POST
    @Path("/register")
    public Response register(@Valid UserDto user) {
        try {
            String adminToken = keycloakService.getAdminToken();
            String userId = keycloakService.createUser(user, adminToken);
            keycloakService.assignRoleToUser(userId, "user", adminToken);

            return Response.status(Response.Status.CREATED)
                    .entity("User registered successfully with role 'user'")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error registering user: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginDto login) {
        try {
            String token = keycloakService.login(login.username, login.password);
            return Response.ok(Collections.singletonMap("token", token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials: " + e.getMessage())
                    .build();
        }
    }
}
