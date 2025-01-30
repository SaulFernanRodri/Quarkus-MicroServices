package example.quarkus.services;


import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.mappers.SoldierMapper;
import example.quarkus.models.Soldier;

import example.quarkus.repositories.SoldierRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SoldierServiceImpl implements SoldierService {

    private final SoldierRepository soldierRepository;

    @Inject
    public SoldierServiceImpl(SoldierRepository soldierRepository) {
        this.soldierRepository = soldierRepository;
    }

    @Override
    public List<ResponseDto> getAllSoldiers(int page) {
        return soldierRepository.findAll().page(Page.of(page, 5)).list()
                .stream().map(SoldierMapper::toResponseDto).toList();
    }

    @Override
    public Optional<ResponseDto> getSoldierById(Long id) {
        return soldierRepository.findByIdOptional(id).map(SoldierMapper::toResponseDto);
    }

    @Override
    @Transactional
    public ResponseDto addSoldier(RequestDto requestDto) {
        Soldier soldier = new Soldier();
        soldier.setName(requestDto.getName());
        soldier.setType(requestDto.getType());
        soldier.setQuantity(requestDto.getQuantity());
        soldierRepository.persist(soldier);
        return SoldierMapper.toResponseDto(soldier);
    }

    @Override
    @Transactional
    public boolean deleteSoldier(Long id) {
        return soldierRepository.deleteById(id);
    }
}
