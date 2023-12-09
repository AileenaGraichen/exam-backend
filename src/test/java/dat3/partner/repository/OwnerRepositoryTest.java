package dat3.partner.repository;

import dat3.partner.dto.OwnerResponse;
import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.entity.UnitStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    LocationRepository locationRepository;

    Location l;
    Owner o1, o2, o3;
    Unit u;
    @BeforeEach
    void setup(){
        o1 = ownerRepository.save(new Owner("Simon", "Hansen", "studOne@kea.dk", "+4520395042"));
        o2 = ownerRepository.save(new Owner("Hans", "Jensen", "studTwo@kea.dk", "+4554893498"));
        o3 = ownerRepository.save(new Owner("Seb", "Jensen", "studThree@kea.dk", "+4509242234"));
        l = locationRepository.save(new Location("DueOdde", "BonBonLandsvej"));
        u = unitRepository.save(new Unit("U001", UnitStatus.AVAILABLE, l, o1, "Type1", "KeyCode1", null));

    }

    @Test
    void testFindByMobile(){
        Owner owner = ownerRepository.findByMobile("+4554893498");
        assertEquals("Hans", owner.getFirstName());
        assertEquals("Jensen", owner.getLastName());
    }

    @Test
    void testExistsByMobile(){
        assertEquals(true, ownerRepository.existsOwnerByMobile("+4554893498"));
    }

    @Test
    void testSearchByUnitNumberExist(){
        List<Owner> owners = ownerRepository.findOwnersByUnitNumber("U001");
        assertEquals(1, owners.size());
        assertEquals("Simon", owners.get(0).getFirstName());
    }

    @Test
    void testSearchByUnitNumberNotExist(){
        List<Owner> owners = ownerRepository.findOwnersByUnitNumber("U002");
        assertEquals(0, owners.size());
    }

    @Test
    void testSearchByNameExist(){
        List<Owner> owners = ownerRepository.findAllBySearchValueIgnoreCase("Simo");
        assertEquals(1, owners.size());
        assertEquals("Simon", owners.get(0).getFirstName());
    }

    @Test
    void testSearchByNameNotExist(){
        List<Owner> owners = ownerRepository.findAllBySearchValueIgnoreCase("laura");
        assertEquals(0, owners.size());
    }

    @Test
    void testSearchByEmailExist(){
        List<Owner> owners = ownerRepository.findAllBySearchValueIgnoreCase("two");
        assertEquals(1, owners.size());
        assertEquals("Hans", owners.get(0).getFirstName());
    }

    @Test
    void testSearchByEmailNotExist(){
        List<Owner> owners = ownerRepository.findAllBySearchValueIgnoreCase("hotmail");
        assertEquals(0, owners.size());
    }
}
