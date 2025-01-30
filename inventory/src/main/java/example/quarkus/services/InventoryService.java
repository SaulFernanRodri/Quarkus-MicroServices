package example.quarkus.services;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Optional<List<ResponseDto>> getAllInventories();

    Optional<ResponseDto> getInventoryById(ObjectId id);

    Optional<ResponseDto> addInventory(RequestDto inventory);

    Optional<ResponseDto> updateInventory(ObjectId id, RequestDto inventory);

    void deleteInventory(ObjectId id);

    Optional<ResponseDto> getInventoryByArmyId(Long armyId);
}
