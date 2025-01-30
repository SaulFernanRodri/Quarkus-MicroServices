package example.quarkus;

public class KeycloakServiceException extends RuntimeException {
    public KeycloakServiceException(String message) {
        super(message);
    }
}
