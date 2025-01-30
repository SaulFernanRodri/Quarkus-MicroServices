package example.quarkus.repositories;

import example.quarkus.models.Soldier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SoldierRepository implements PanacheRepository<Soldier> {
}