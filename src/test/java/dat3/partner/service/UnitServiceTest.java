package dat3.partner.service;

import dat3.partner.entity.Unit;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
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



    @BeforeEach
    void setup(){
        //Setup objects and save in repos

        unitService = new UnitService(unitRepository, locationRepository, ownerRepository);
    }



}
