package example.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KeycloakConfig {

    @ConfigProperty(name = "keycloak.url")
    String keycloakUrl;

    @ConfigProperty(name = "keycloak.realm")
    String realm;

    @ConfigProperty(name = "keycloak.client-id")
    String clientId;

    @ConfigProperty(name = "keycloak.admin-client-id")
    String adminClientId;

    @ConfigProperty(name = "keycloak.admin-username")
    String adminUsername;

    @ConfigProperty(name = "keycloak.admin-password")
    String adminPassword;

    @ConfigProperty(name = "keycloak.client-secret")
    String adminClientSecret;

    public String getKeycloakUrl() {
        return keycloakUrl;
    }

    public String getRealm() {
        return realm;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAdminClientId() {
        return adminClientId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}
