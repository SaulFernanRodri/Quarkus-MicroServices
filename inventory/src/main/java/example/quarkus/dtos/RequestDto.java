package example.quarkus.dtos;

import example.quarkus.models.Inventory;
import lombok.Data;

import java.util.List;

@Data
public class RequestDto {
    private String name;
    private String description;
    private Long armyId;
    private String location;
    private String status;
    private List<Inventory.Item> items;
}
