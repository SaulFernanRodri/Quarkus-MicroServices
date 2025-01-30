package example.quarkus.dtos;

import lombok.Data;

import java.util.List;

@Data
public class InventoryDto {
    private String name;
    private String description;
    private String location;
    private String status;
    private List<ItemDto> items;

    @Data
    public static class ItemDto {
        private String name;
        private String type;
        private String serialNumber;
        private int quantity;
        private String manufacturer;
    }
}
