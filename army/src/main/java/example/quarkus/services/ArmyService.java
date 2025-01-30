package example.quarkus.services;

import example.quarkus.dtos.ArmyInventoryDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Army;

import java.util.List;

public interface ArmyService {

    List<ResponseDto> getArmy();

    ResponseDto getArmyById(Long id);

    ResponseDto createArmy(Army army);

    ResponseDto updateArmy(Long id, Army army);

    void deleteArmy(Long id);

    Army getArmyWithSoldier(Long id);

    Army addSoldierToArmy(Long id, Long soldierId);

    void removeSoldierFromArmy(Long id, Long soldierId);

    ArmyInventoryDto getArmyWithInventory(Long id);

}
