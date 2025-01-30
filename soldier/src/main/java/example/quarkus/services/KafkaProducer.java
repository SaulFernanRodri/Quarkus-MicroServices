package example.quarkus.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaProducer {

    @Channel("soldier")
    Emitter<String> soldierEmitter;

    public void sendCreate(String message) {
        soldierEmitter.send(message);
    }

    @Channel("soldier-update")
    Emitter<String> soldierUpdateEmitter;

    public void sendUpdate(String message) {
        soldierUpdateEmitter.send(message);
    }

}
