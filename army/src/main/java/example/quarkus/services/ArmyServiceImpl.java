package example.quarkus.services;

import example.quarkus.dtos.ArmyInventoryDto;
import example.quarkus.dtos.InventoryDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.mapper.ArmyMapper;
import example.quarkus.models.Army;
import example.quarkus.models.Soldier;
import example.quarkus.repositories.ArmyRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class ArmyServiceImpl implements ArmyService {

    private final ArmyRepository armyRepository;
    private final SoldierServiceClient soldierServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    @Inject
    public ArmyServiceImpl(ArmyRepository armyRepository, @RestClient SoldierServiceClient soldierServiceClient, @RestClient InventoryServiceClient inventoryServiceClient) {
        this.armyRepository = armyRepository;
        this.soldierServiceClient = soldierServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @Override
    public List<ResponseDto> getArmy() {
        return armyRepository.findAll().stream()
                .map(ArmyMapper::toResponseDto)
                .toList();
    }

    @Override
    public ResponseDto getArmyById(Long id) {
        return armyRepository.findByIdOptional(id)
                .map(ArmyMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public ResponseDto createArmy(Army army) {
        armyRepository.persist(army);
        return ArmyMapper.toResponseDto(army);
    }

    @Override
    @Transactional
    public ResponseDto updateArmy(Long id, Army army) {
        return armyRepository.findByIdOptional(id)
                .map(a -> {
                    a.setName(army.getName());
                    a.setCountry(army.getCountry());
                    a.setType(army.getType());
                    a.setCommander(army.getCommander());
                    armyRepository.persist(a);
                    return ArmyMapper.toResponseDto(a);
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteArmy(Long id) {
        armyRepository.deleteById(id);
    }

    @Override
    public Army getArmyWithSoldier(Long id) {
        Army army = armyRepository.findById(id);
        if (army != null) {
            List<Soldier> soldiers = soldierServiceClient.getAllSoldiers();
            army.getSoldiers().forEach(soldier ->
                    soldiers.stream()
                            .filter(s -> soldier.getSoldierId().equals(s.getId()))
                            .findFirst()
                            .ifPresent(s -> {
                                soldier.setName(s.getName());
                                soldier.setQuantity(s.getQuantity());
                            })
            );
        }
        return army;
    }

    @Override
    @Transactional
    public Army addSoldierToArmy(Long armyId, Long soldierID) {
        Army army = armyRepository.findById(armyId);
        if (army == null) {
            throw new IllegalStateException("Army not found");
        }

        Soldier soldier = soldierServiceClient.getSoldierById(soldierID);

        if (soldier == null) {
            throw new IllegalStateException("Soldier not found");
        }
        boolean soldierExists = army.getSoldiers().stream()
                .anyMatch(s -> s.getSoldierId().equals(soldier.getId()));

        if (!soldierExists) {
            Soldier newSoldier = new Soldier();
            newSoldier.setArmy(army);
            newSoldier.setName(soldier.getName());
            newSoldier.setQuantity(soldier.getQuantity());
            army.getSoldiers().add(newSoldier);
            armyRepository.persist(army);
        } else {
            throw new IllegalStateException("Soldier already added to the army");
        }

        return army;
    }

    @Override
    @Transactional
    public void removeSoldierFromArmy(Long armyId, Long soldierId) {
        Army army = armyRepository.findById(armyId);
        if (army == null) {
            throw new IllegalStateException("Army not found");
        }

        Soldier soldierToRemove = army.getSoldiers().stream()
                .filter(s -> s.getSoldierId().equals(soldierId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Soldier not found"));

        army.getSoldiers().remove(soldierToRemove);
        armyRepository.getEntityManager().remove(soldierToRemove);

        armyRepository.persist(army);
    }

    @Override
    public ArmyInventoryDto getArmyWithInventory(Long id) {
        Army army = armyRepository.findById(id);
        if (army != null) {
            InventoryDto inventory = inventoryServiceClient.getInventoryByArmyId(id);
            return ArmyMapper.mapToArmyInventoryDto(army, inventory);
        }
        return null;
    }
}
