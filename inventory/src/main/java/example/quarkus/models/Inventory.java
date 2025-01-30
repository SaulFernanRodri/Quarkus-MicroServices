package example.quarkus.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MongoEntity(collection = "inventory")
@Data
public class Inventory {

    private ObjectId id;
    private String name;
    private String description;
    private Long armyId;
    private String location;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Item> items;

    @Data
    public static class Item {
        private String id;
        private String name;
        private String type;
        private String serialNumber;
        private int quantity;
        private String manufacturer;
        private LocalDate purchaseDate;
        private LocalDate lastMaintenance;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
