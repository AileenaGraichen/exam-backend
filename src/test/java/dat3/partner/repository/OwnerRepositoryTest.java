package dat3.partner.repository;

import dat3.partner.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class OwnerRepositoryTest {

    @Autowired
    OwnerRepository ownerRepository;

    @BeforeEach
    void setup(){
        ownerRepository.save(new Owner("Simon", "Hansen", "stud@kea.dk", "+4520395042"));
        ownerRepository.save(new Owner("Hans", "Jensen", "stud@kea.dk", "+4520395042"));
        ownerRepository.save(new Owner("Seb", "Jensen", "stud@kea.dk", "+4520395042"));

    }

    @Test
    void testFindByNameContainingSucces(){
    }
}
