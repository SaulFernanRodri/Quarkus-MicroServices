package example.quarkus.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseDto {
    private String name;
    private String description;
    private Long armyId;
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
        private LocalDate purchaseDate;
        private LocalDate lastMaintenance;
    }
}
