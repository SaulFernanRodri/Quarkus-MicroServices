package example.quarkus.mapper;

import example.quarkus.dtos.ArmyInventoryDto;
import example.quarkus.dtos.InventoryDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Army;

public class ArmyMapper {

    private ArmyMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ResponseDto toResponseDto(Army army) {
        return new ResponseDto(army.getName(), army.getCountry(), army.getType(), army.getCommander());
    }

    public static ArmyInventoryDto mapToArmyInventoryDto(Army army, InventoryDto inventory) {
        ArmyInventoryDto dto = new ArmyInventoryDto();

        dto.setArmyName(army.getName());
        dto.setCountry(army.getCountry());
        dto.setType(army.getType());
        dto.setCommander(army.getCommander());

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setName(inventory.getName());
        inventoryDto.setDescription(inventory.getDescription());
        inventoryDto.setLocation(inventory.getLocation());
        inventoryDto.setStatus(inventory.getStatus());
        inventoryDto.setItems(inventory.getItems().stream().map(item -> {
            InventoryDto.ItemDto  itemDto = new InventoryDto.ItemDto();
            itemDto.setName(item.getName());
            itemDto.setType(item.getType());
            itemDto.setSerialNumber(item.getSerialNumber());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setManufacturer(item.getManufacturer());
            return itemDto;
        }).toList());

        dto.setInventory(inventoryDto);

        return dto;
    }

}
