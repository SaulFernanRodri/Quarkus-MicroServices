package example.quarkus.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"armyId", "soldierId"}))
public class Soldier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armyId", referencedColumnName = "id")
    @JsonBackReference
    private Army army;

    @Column
    private Long soldierId;

    @Transient
    private String name;

    @Transient
    private int quantity;
}
