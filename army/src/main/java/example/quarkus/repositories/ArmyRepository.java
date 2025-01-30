package example.quarkus.repositories;

import example.quarkus.models.Army;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ArmyRepository implements PanacheRepository<Army> {
}
