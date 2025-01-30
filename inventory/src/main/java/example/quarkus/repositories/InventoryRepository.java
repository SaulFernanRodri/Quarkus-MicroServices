package example.quarkus.repositories;

import example.quarkus.models.Inventory;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryRepository implements PanacheMongoRepository<Inventory> {
}
