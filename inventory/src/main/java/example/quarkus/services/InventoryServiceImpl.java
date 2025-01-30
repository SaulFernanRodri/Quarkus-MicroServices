package example.quarkus.services;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.mappers.InventoryMapper;
import example.quarkus.models.Inventory;
import example.quarkus.repositories.InventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InventoryServiceImpl implements InventoryService {


    InventoryRepository inventoryRepository;

    @Inject
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Optional<List<ResponseDto>> getAllInventories() {
        return Optional.of(inventoryRepository.findAll().stream()
                .map(InventoryMapper::toResponseDto).toList());
    }

    @Override
    public Optional<ResponseDto> getInventoryById(ObjectId id) {
        return Optional.ofNullable(inventoryRepository.findById(id)).map(InventoryMapper::toResponseDto);
    }

    @Override
    public Optional<ResponseDto> addInventory(RequestDto inventory) {
        Inventory inv = InventoryMapper.toInventory(inventory);
        inventoryRepository.persist(inv);
        return Optional.of(inv).map(InventoryMapper::toResponseDto);
    }

    @Override
    public Optional<ResponseDto> updateInventory(ObjectId id, RequestDto inventory) {
        Inventory existingInventory = inventoryRepository.findById(id);
        if (existingInventory != null) {
            existingInventory.setName(inventory.getName());
            inventoryRepository.update(existingInventory);
            return Optional.of(existingInventory).map(InventoryMapper::toResponseDto);
        }
        return Optional.empty();
    }

    @Override
    public void deleteInventory(ObjectId id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public Optional<ResponseDto> getInventoryByArmyId(Long armyId) {
        return Optional.of(InventoryMapper.toResponseDto(inventoryRepository.find("armyId", armyId).firstResult()));
    }

}
