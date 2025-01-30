package example.quarkus.mappers;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Inventory;

public class InventoryMapper {

    private InventoryMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Inventory toInventory(RequestDto requestDto) {
        Inventory inventory = new Inventory();
        inventory.setName(requestDto.getName());
        inventory.setDescription(requestDto.getDescription());
        inventory.setArmyId(requestDto.getArmyId());
        inventory.setLocation(requestDto.getLocation());
        inventory.setStatus(requestDto.getStatus());
        inventory.setItems(requestDto.getItems().stream().map(itemDto -> {
            Inventory.Item item = new Inventory.Item();
            item.setName(itemDto.getName());
            item.setType(itemDto.getType());
            item.setSerialNumber(itemDto.getSerialNumber());
            item.setQuantity(itemDto.getQuantity());
            item.setManufacturer(itemDto.getManufacturer());
            return item;
        }).toList());

        return inventory;

    }

    public static ResponseDto toResponseDto(Inventory inventory) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setName(inventory.getName());
        responseDto.setDescription(inventory.getDescription());
        responseDto.setArmyId(inventory.getArmyId());
        responseDto.setLocation(inventory.getLocation());
        responseDto.setStatus(inventory.getStatus());
        responseDto.setItems(inventory.getItems().stream().map(item -> {
            ResponseDto.ItemDto itemDto = new ResponseDto.ItemDto();
            itemDto.setName(item.getName());
            itemDto.setType(item.getType());
            itemDto.setSerialNumber(item.getSerialNumber());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setManufacturer(item.getManufacturer());
            return itemDto;
        }).toList());

        return responseDto;
    }
}
