package dat3.partner.service;

import dat3.partner.dto.UnitRequest;
import dat3.partner.dto.UnitResponse;
import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.entity.UnitStatus;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class UnitServiceTest {
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OwnerRepository ownerRepository;
    UnitService unitService;
    Location l1;
    Owner o1;

    @BeforeEach
    void setup(){
        l1 = locationRepository.save(new Location("DueOdde", "Havnevej 23"));
        o1 = ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        //Add cleaning plan and maintenance tasks here after implementation of api.
        //Fix constructor for this too.
        unitRepository.save(new Unit("U001", UnitStatus.AVAILABLE, l1, o1, "Type1", "KeyCode1"));

        unitService = new UnitService(unitRepository, locationRepository, ownerRepository);
    }

    @Test
    void testAddUnitSuccess(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        UnitResponse response = unitService.addUnit(body);
        assertEquals(2, response.getId());
    }
    @Test
    void testAddUnitAlreadyExistsException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U001")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.addUnit(body));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
    @Test
    void testAddUnitLocationNotFoundException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(3)
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.addUnit(body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testAddUnitOwnerNotFoundException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(3)
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.addUnit(body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testEditUnitSucces(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
    }
    @Test
    void testEditUnitNotFoundException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.editUnit(3, body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testEditUnitOwnerNotFoundException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(l1.getId())
                .ownerId(3)
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.editUnit(1, body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testEditUnitLocationNotFoundException(){
        UnitRequest body = UnitRequest.builder()
                .unitNumber("U23")
                .unitStatus(UnitStatus.IN_PROGRESS)
                .locationId(4)
                .ownerId(o1.getId())
                .type("2-4 pers.")
                .keyCode("2468#")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.editUnit(1, body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testDeleteUnitSuccess(){

    }
    @Test
    void testDeleteUnitNotFoundException(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> unitService.deleteUnitById(10));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
