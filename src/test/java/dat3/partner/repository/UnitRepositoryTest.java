package dat3.partner.repository;


import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.entity.UnitStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class UnitRepositoryTest {

    @Autowired
    UnitRepository unitRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @BeforeEach
    void setup(){
        Location l1 = locationRepository.save(new Location("DueOdde", "Havnevej 23"));
        Owner o1 = ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        unitRepository.save(new Unit("U001", UnitStatus.AVAILABLE, l1, o1, "Type1", "KeyCode1"));
    }

    @Test
    void testExistsByLocationIdAndUnitNumber(){
        boolean result = unitRepository.existsByLocationIdAndAndUnitNumber(1, "U001");
        assertTrue(result);
    }
}
