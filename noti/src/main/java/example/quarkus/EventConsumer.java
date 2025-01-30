package example.quarkus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.logging.Logger;

@ApplicationScoped
public class EventConsumer {


    private static final Logger logger = Logger.getLogger(EventConsumer.class.getName());
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @Inject
    public EventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.objectMapper = new ObjectMapper();
    }

    @Incoming("soldier-mail")
    public void consumeUpdate(String message) {
        try {
            UpdateMail updateMail = objectMapper.readValue(message, UpdateMail.class);
            String email;
            for (String name : updateMail.getNames()) {
                email = name.replaceAll("\\s", "").toLowerCase() + "@example.com";
                notificationService.sendSoldierNoti(updateMail, email).subscribe().with(
                        ssuccess -> logger.info("Email sent to " + name),
                        failure -> logger.severe("Failed to send email to " + name)
                );
            }
        } catch (Exception e) {
            logger.severe("Failed to parse message: " + message);
        }
    }

}
