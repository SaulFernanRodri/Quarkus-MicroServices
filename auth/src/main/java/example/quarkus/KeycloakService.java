package example.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Form;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;

@ApplicationScoped
public class KeycloakService {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String PASSWORD_PARAM = "password";

    private final KeycloakConfig config;
    private final KeycloakRestClient keycloakRestClient;

    @Inject
    public KeycloakService(KeycloakConfig config, @RestClient KeycloakRestClient keycloakRestClient) {
        this.config = config;
        this.keycloakRestClient = keycloakRestClient;
    }

    public String getAdminToken() {
        Form form = new Form()
                .param("client_id", config.adminClientId)
                .param("client_secret", config.adminClientSecret)
                .param("grant_type", PASSWORD_PARAM)
                .param("username", config.adminUsername)
                .param("password", config.adminPassword);

        try {
            return keycloakRestClient.getToken(config.realm, form);
        } catch (Exception e) {
            throw new KeycloakServiceException("Failed to retrieve admin token" + e.getMessage());
        }
    }

    public String createUser(UserDto user, String adminToken) {
        try {
            return keycloakRestClient.createUser(config.realm, BEARER_PREFIX + adminToken, user);
        } catch (Exception e) {
            throw new KeycloakServiceException("Error creating user"+ e.getMessage());
        }
    }

    public void assignRoleToUser(String userId, String roleName, String adminToken) {
        try {
            String roleRepresentation = keycloakRestClient.getRole(config.realm, BEARER_PREFIX + adminToken, roleName);
            keycloakRestClient.assignRole(config.realm, userId, BEARER_PREFIX + adminToken, Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            throw new KeycloakServiceException("Error assigning role: " + roleName+ e.getMessage());
        }
    }

    public String login(String username, String password) {
        Form form = new Form()
                .param("client_id", config.clientId)
                .param("client_secret", config.adminClientSecret)
                .param("grant_type", PASSWORD_PARAM)
                .param("username", username)
                .param("password", password);

        try {
            return keycloakRestClient.getToken(config.realm, form);
        } catch (Exception e) {
            throw new KeycloakServiceException("Invalid credentials"+ e.getMessage());
        }
    }
}
