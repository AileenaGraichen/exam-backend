package dat3.partner.service;

import dat3.partner.dto.LocationRequest;
import dat3.partner.dto.LocationResponse;
import dat3.partner.entity.Location;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class LocationServiceTest {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UnitRepository unitRepository;

    LocationService locationService;

    Location l1, l2, l3, l4;

    @BeforeEach
    void setup(){
        l1 = new Location("DueOdde", "Nørrebrogade 10");
        l2 = new Location("Helleborg", "Guldbergsgade");
        l3 = new Location("Dyrepark", "Gudhjemsvej");
        l4 = new Location("Sildebenet", "Makrelvej");
        locationRepository.save(l1);
        locationRepository.save(l2);
        locationRepository.save(l3);
        locationRepository.save(l4);
        locationService = new LocationService(locationRepository, unitRepository);
    }

    @Test
    void testGetOneLocationSuccess(){
        LocationResponse location = locationService.getOneLocation(1);
        assertEquals(location.getLocationName(), "DueOdde");
    }

    @Test
    void testGetOneLocationNotFoundException(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> locationService.getOneLocation(5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testAddLocationSuccess(){
        LocationRequest request = LocationRequest.builder()
                .locationName("Skibshavn")
                .address("havnestriben")
                .build();
        LocationResponse res = locationService.addLocation(request);
        assertEquals(5, res.getId());
        assertEquals("Skibshavn", res.getLocationName());
        assertEquals("havnestriben", res.getAddress());

    }

    @Test
    void testAddLocationNameAlreadyExistsException(){
        LocationRequest request = LocationRequest.builder()
                .locationName("Dyrepark")
                .address("dyrestriben")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> locationService.addLocation(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testEditLocationSuccess(){
        LocationRequest request = LocationRequest.builder()
                .locationName("BonBon Land")
                .address("gartnervej 2")
                .build();
        LocationResponse res = locationService.editLocation(2, request);
        assertEquals("BonBon Land", res.getLocationName());
        assertEquals("gartnervej 2", res.getAddress());
    }

    @Test
    void testEditLocationNotFoundException(){
        LocationRequest request = LocationRequest.builder()
                .locationName("Skibshavn")
                .address("havnestriben")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> locationService.editLocation(10, request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }


    //TODO Finish DeleteLocation tests after service method implemented
    void testDeleteLocationSuccess(){}


    //Throws bad request if units are connected to the location
    void testDeleteLocationBadRequestException(){}


}
