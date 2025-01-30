package example.quarkus.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    @Channel("soldier-mail")
    private final Emitter<String> emitter;
    private final ObjectMapper objectMapper;

    @Inject
    public KafkaProducer(@Channel("soldier-mail") Emitter<String> emitter) {
        this.emitter = emitter;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(String message, List<String> names) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("names", names);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            emitter.send(jsonPayload);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to send message: " + e.getMessage());
        }
    }
}