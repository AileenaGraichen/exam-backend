package dat3.partner.service;

import dat3.partner.dto.OwnerRequest;
import dat3.partner.dto.OwnerResponse;
import dat3.partner.entity.Owner;
import dat3.partner.repository.OwnerRepository;
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
public class OwnerServiceTest {

    @Autowired
    OwnerRepository ownerRepository;
    OwnerService ownerService;


    @BeforeEach
    void setup(){
        ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        ownerRepository.save(new Owner("Jane", "Smith", "jane.smith@example.com", "9876543210"));
        ownerRepository.save(new Owner("Alice", "Johnson", "alice.johnson@example.com", "5678901234"));

        ownerService = new OwnerService(ownerRepository);
    }

    @Test
    void testAddOwnerSuccess(){
        OwnerRequest body = OwnerRequest.builder()
                .firstName("Bob")
                .lastName("Williams")
                .email("bob.williams@example.com")
                .mobile("6789012345")
                .build();
        OwnerResponse response = ownerService.addOwner(body);
        assertEquals(4, response.getId());
    }

    @Test
    void testAddOwnerPhoneAlreadyExistsException(){
        OwnerRequest body = OwnerRequest.builder()
                .firstName("Bob")
                .lastName("Williams")
                .email("bob.williams@example.com")
                .mobile("9876543210")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ownerService.addOwner(body));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testEditOwnerSuccess(){
        OwnerRequest body = OwnerRequest.builder()
                .firstName("Danny")
                .lastName("Devito")
                .email("stud.kea@stud.dk")
                .mobile("9876543210")
                .build();
        OwnerResponse response = ownerService.editOwner(3, body);
        assertEquals("Devito", response.getLastName());
    }
    @Test
    void testEditOwnerNonExistException(){
        OwnerRequest body = OwnerRequest.builder()
                .firstName("Danny")
                .lastName("Devito")
                .email("stud.kea@stud.dk")
                .mobile("9876543210")
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ownerService.editOwner(6, body));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testDeleteOwnerSuccess(){
        ownerService.deleteOwner(2);
        assertEquals(false, ownerRepository.existsById(2));
    }

    @Test
    void testDeleteOwnerNonExistException(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ownerService.deleteOwner(10));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}
