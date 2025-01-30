package example.quarkus.services;

import example.quarkus.models.Army;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.List;

@Slf4j
@ApplicationScoped
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private  final ArmyService armyService;
    private final KafkaProducer kafkaProducer;

    @Inject
    public KafkaConsumer(ArmyService armyService, KafkaProducer kafkaProducer) {
        this.armyService = armyService;
        this.kafkaProducer = kafkaProducer;
    }

    @Incoming("soldier")
    public void consumeCreate(String message) {
        LOGGER.info("Create: " + message);
    }

    @Incoming("soldier-update")
    public void consume(String message) {
        /* Review
        List<Army> armies = armyService.getArmyBySymbolCode(message);
        LOGGER.info("Update: " + message);
        List<String> names = armies.stream().map(Army::getName).toList();
        kafkaProducer.sendMessage(message, names); */
    }
}