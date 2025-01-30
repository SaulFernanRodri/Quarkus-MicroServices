package example.quarkus;

import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.mailer.Mail;

@ApplicationScoped
public class NotificationService {


    ReactiveMailer mailer;

    @Inject
    public NotificationService( ReactiveMailer mailer) {
        this.mailer = mailer;
    }

    public Uni<Void> sendSoldierNoti(UpdateMail message, String recipient) {
        String body = """
                Hi,
                
                A soldier has been updated
                
                Best regards,
                """;

        String subject = "Soldier Updated: " + message.getMessage();

        return mailer.send(
                Mail.withText(recipient, subject, body)
        );
    }

}
