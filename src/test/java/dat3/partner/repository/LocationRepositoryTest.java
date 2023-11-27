package dat3.partner.repository;

import dat3.partner.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @BeforeEach
    void setup(){
        locationRepository.save(new Location("DueOdde", "BonBonLandsvej"));
        locationRepository.save(new Location("Bornholm Art Museum", "Gudhjemvej, 25, 3760 Gudhjem"));
        locationRepository.save(new Location("Helligdomsklipperne", "Rø Plantagevej, 3770 Allinge"));
    }

    @Test
    void testLocationExistsByName(){
        assertEquals(true, locationRepository.existsLocationByLocationName("Helligdomsklipperne"));
    }


}
