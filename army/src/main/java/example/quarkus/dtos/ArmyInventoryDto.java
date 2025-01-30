package example.quarkus.dtos;

import lombok.Data;

@Data
public class ArmyInventoryDto{
    private String armyName;
    private String country;
    private String type;
    private String commander;
    private InventoryDto inventory;
}
