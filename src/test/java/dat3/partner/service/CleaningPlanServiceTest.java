package dat3.partner.service;

import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.entity.*;
import dat3.partner.repository.CleaningPlanRepository;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class CleaningPlanServiceTest {
    @Autowired
    CleaningPlanRepository cleaningPlanRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    UserWithRolesRepository userWithRolesRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OwnerRepository ownerRepository;
    CleaningPlanService cleaningPlanService;

    UserWithRoles us1, us2;
    Unit u1, u2;
    CleaningPlan cp1, cp2;
    Location l1;
    Owner o1;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @BeforeEach
    void setup(){
        l1 = locationRepository.save(new Location("Dueodde", "DueoddeVej"));
        o1 = ownerRepository.save(new Owner("Jens", "Hansen", "stud@kea.dk", "+4520394032"));
        us1 = userWithRolesRepository.save(new UserWithRoles("user1", "test2", "stud@kea.dk"));
        u1 = unitRepository.save(new Unit("U001", UnitStatus.AVAILABLE, l1, o1, "Type1", "KeyCode1"));
        cleaningPlanRepository.save(new CleaningPlan(LocalDate.parse("26/11/2023", formatter), u1, us1));
        cleaningPlanService = new CleaningPlanService(cleaningPlanRepository, unitRepository, userWithRolesRepository);
    }

    @Test
    void testAddCleaningPlanSuccess(){
        CleaningPlanRequest plan1 = CleaningPlanRequest.builder()
                .unitId(1)
                .userName("user1")
                .date(LocalDate.parse("27/11/2023", formatter))
                .build();
        CleaningPlanRequest plan2 = CleaningPlanRequest.builder()
                .unitId(1)
                .userName("user1")
                .date(LocalDate.parse("28/11/2023", formatter))
                .build();
        List<CleaningPlanRequest> newPlans = new ArrayList<>();
        newPlans.add(plan1);
        newPlans.add(plan2);
        cleaningPlanService.addCleaningPlan(newPlans);
        List<CleaningPlan> result = cleaningPlanRepository.findAll();
        assertEquals(3, result.size());
    }
    @Test
    void testAddCleaningPlanNoUserException(){
        CleaningPlanRequest plan1 = CleaningPlanRequest.builder()
                .unitId(1)
                .userName("user3")
                .date(LocalDate.parse("26/11/2023", formatter))
                .build();
        List<CleaningPlanRequest> newPlans = new ArrayList<>();
        newPlans.add(plan1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cleaningPlanService.addCleaningPlan(newPlans));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void testAddCleaningPlanNoUnitException(){
        CleaningPlanRequest plan1 = CleaningPlanRequest.builder()
                .unitId(3)
                .userName("user1")
                .date(LocalDate.parse("26/11/2023", formatter))
                .build();
        List<CleaningPlanRequest> newPlans = new ArrayList<>();
        newPlans.add(plan1);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cleaningPlanService.addCleaningPlan(newPlans));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deleteCleaningPlanSuccess(){
        CleaningPlanRequest plan1 = CleaningPlanRequest.builder()
                .unitId(1)
                .userName("user1")
                .date(LocalDate.parse("26/11/2023", formatter))
                .build();
        List<CleaningPlanRequest> deletePlan = new ArrayList<>();
        deletePlan.add(plan1);
        cleaningPlanService.deleteCleaningPlan(deletePlan);
        List<CleaningPlan> result = cleaningPlanRepository.findAll();
        assertEquals(0, result.size());
    }

}
