package dat3.partner.service;

import dat3.partner.entity.*;
import dat3.partner.repository.CleaningPlanRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class CleaningPlanServiceTest {
    @Autowired
    CleaningPlanRepository cleaningPlanRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    UserWithRolesRepository userWithRolesRepository;
    CleaningPlanService cleaningPlanService;

    UserWithRoles us1, us2;
    Unit u1, u2;
    CleaningPlan cp1, cp2;
    Location l1;
    Owner o1;

    @BeforeEach
    void setup(){
        l1 = new Location("Dueodde", "DueoddeVej");
        o1 = new Owner("Jens", "Hansen", "stud@kea.dk", "+4520394032");
        us1 = new UserWithRoles("user1", "test2", "stud@kea.dk");
        u1 = new Unit("U001", UnitStatus.AVAILABLE, l1, o1, "Type1", "KeyCode1");
        cleaningPlanRepository.save(new CleaningPlan(LocalDate.parse("26/11/2023"), u1, us1));
        cleaningPlanService = new CleaningPlanService(cleaningPlanRepository, unitRepository, userWithRolesRepository);
    }

    void testAddCleaningPlanSuccess(){}
    void testAddCleaningPlanNoUserException(){}
    void testAddCleaningPlanNoUnitException(){}
    void testAddCleaningPlanAlreadyExistsException(){}

    void testEditCleaningPlanSuccess(){}
    void testEditCleaningPlanNotFoundException(){}


    void deleteCleaningPlanSuccess(){}
    void deleteCleaningPlanNotFoundException(){}

}
