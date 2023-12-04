package dat3.partner.service;

import dat3.partner.dto.MaintenanceTaskRequest;
import dat3.partner.dto.MaintenanceTaskResponse;
import dat3.partner.entity.*;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.MaintenanceTaskRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class MaintenanceTaskServiceTest {
    @Autowired
    MaintenanceTaskRepository maintenanceTaskRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    UserWithRolesRepository userWithRolesRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    LocationRepository locationRepository;
    MaintenanceTaskService maintenanceTaskService;
    String passwordUsedByAll = "test12";

    UserWithRoles user1, user2;
    Location location;
    Owner owner;
    Unit unit1, unit2;
    MaintenanceTask task1, task2;
    @BeforeEach
    void setUp(){
        user1 = userWithRolesRepository.save(new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk"));
        user2 = userWithRolesRepository.save(new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk"));
        location = locationRepository.save(new Location("DueOdde", "Havnevej 23"));
        owner = ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        unit1 = unitRepository.save(new Unit("U001", UnitStatus.AVAILABLE, location, owner, "Type1", "KeyCode1"));
        unit2 = unitRepository.save( new Unit("U002", UnitStatus.IN_PROGRESS, location, owner, "Type2", "KeyCode2"));
        task1 = maintenanceTaskRepository.save(new MaintenanceTask("Description 1", "Title 1", MaintenanceStatus.NOT_STARTED, MaintenancePriority.HIGH, user1, unit1, null));
        task2 = maintenanceTaskRepository.save(new MaintenanceTask("Description 2", "Title 2", MaintenanceStatus.IN_PROGRESS, MaintenancePriority.MEDIUM, user2, unit2, null));
        maintenanceTaskService = new MaintenanceTaskService(maintenanceTaskRepository, userWithRolesRepository, unitRepository);
    }

    //@Test
    void testAddMaintenanceTaskSuccess(){
        MaintenanceTaskRequest body = MaintenanceTaskRequest.builder()
                .title("Title3")
                .description("Description3")
                .status(MaintenanceStatus.DONE)
                .priority(MaintenancePriority.HIGH)
                .accountUsername("user1")
                .unitId(unit1.getId())
                .image(null).build();
        MaintenanceTaskResponse response = maintenanceTaskService.createMaintenanceTask(body);
        assertEquals("Description3", response.getDescription());
        assertEquals(unit1.getId(), response.getUnitId());
    }
}