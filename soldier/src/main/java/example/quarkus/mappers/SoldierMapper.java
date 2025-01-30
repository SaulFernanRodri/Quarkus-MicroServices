package example.quarkus.mappers;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Soldier;

public class SoldierMapper {

    private SoldierMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ResponseDto toResponseDto(Soldier soldier) {
        return new ResponseDto(
                soldier.getId(),
                soldier.getName(),
                soldier.getType(),
                soldier.getQuantity()
        );
    }

    public static Soldier toEntity(RequestDto dto) {
        Soldier soldier = new Soldier();

        soldier.setName(dto.getName());
        soldier.setType(dto.getType());
        soldier.setQuantity(dto.getQuantity());

        return soldier;
    }
}
