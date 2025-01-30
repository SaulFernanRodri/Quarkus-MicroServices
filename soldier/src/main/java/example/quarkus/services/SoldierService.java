package example.quarkus.services;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;

import java.util.List;
import java.util.Optional;

public interface SoldierService {

    List<ResponseDto> getAllSoldiers(int page);

    Optional<ResponseDto> getSoldierById(Long id);

    ResponseDto addSoldier(RequestDto requestDto);

    boolean deleteSoldier(Long id);
}