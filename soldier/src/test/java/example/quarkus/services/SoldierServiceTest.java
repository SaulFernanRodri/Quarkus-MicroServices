package example.quarkus.services;

import example.quarkus.dtos.RequestDto;
import example.quarkus.dtos.ResponseDto;
import example.quarkus.models.Soldier;
import example.quarkus.repositories.SoldierRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

@QuarkusTest
class SoldierServiceTest {

    @Mock
    private SoldierRepository soldierRepository;

    @InjectMocks
    private SoldierServiceImpl soldierServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSoldierById_found() {
        Long id = 1L;
        Soldier mockEntity = new Soldier();
        mockEntity.setId(id);
        mockEntity.setName("John Doe");
        mockEntity.setType("Infantry");
        mockEntity.setQuantity(50);

        ResponseDto expectedDto = new ResponseDto(id, "John Doe", "Infantry", 50);

        Mockito.when(soldierRepository.findByIdOptional(id)).thenReturn(Optional.of(mockEntity));

        Optional<ResponseDto> result = soldierServiceImpl.getSoldierById(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedDto.name(), result.get().name());
        Assertions.assertEquals(expectedDto.quantity(), result.get().quantity());
        Mockito.verify(soldierRepository, Mockito.atLeastOnce()).findByIdOptional(id);
    }

    @Test
    void testGetSoldierById_notFound() {
        Long id = 1L;
        Mockito.when(soldierRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        Optional<ResponseDto> result = soldierServiceImpl.getSoldierById(id);

        Assertions.assertFalse(result.isPresent());
        Mockito.verify(soldierRepository, Mockito.atLeastOnce()).findByIdOptional(id);
    }

    @Test
    void testAddSoldier() {
        RequestDto requestDto = new RequestDto();
        requestDto.setName("John Doe");
        requestDto.setType("Infantry");
        requestDto.setQuantity(50);

        Soldier soldier = new Soldier();
        soldier.setName(requestDto.getName());
        soldier.setType(requestDto.getType());
        soldier.setQuantity(requestDto.getQuantity());

        Mockito.doNothing().when(soldierRepository).persist(soldier);

        ResponseDto result = soldierServiceImpl.addSoldier(requestDto);

        Assertions.assertEquals(requestDto.getName(), result.name());
        Assertions.assertEquals(requestDto.getQuantity(), result.quantity());
        Mockito.verify(soldierRepository, Mockito.atLeastOnce()).persist(Mockito.any(Soldier.class));
    }

    @Test
    void testDeleteSoldier() {
        Long id = 1L;
        Mockito.when(soldierRepository.deleteById(id)).thenReturn(true);

        boolean result = soldierServiceImpl.deleteSoldier(id);

        Assertions.assertTrue(result);
        Mockito.verify(soldierRepository, Mockito.times(1)).deleteById(id);
    }
}
