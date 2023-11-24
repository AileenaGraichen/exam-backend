package dat3.partner.repository;

import dat3.partner.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @BeforeEach
    void setup(){
        ownerRepository.save(new Owner("Simon", "Hansen", "stud@kea.dk", "+4520395042"));
        ownerRepository.save(new Owner("Hans", "Jensen", "stud@kea.dk", "+4554893498"));
        ownerRepository.save(new Owner("Seb", "Jensen", "stud@kea.dk", "+4509242234"));

    }

    //@Test
    void testFindByNameContainingSucces(){
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
}
