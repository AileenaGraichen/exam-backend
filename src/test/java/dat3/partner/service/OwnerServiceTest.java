package dat3.partner.service;

import dat3.partner.entity.Owner;
import dat3.partner.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class OwnerServiceTest {

    @Autowired
    OwnerRepository ownerRepository;

    Owner o1, o2, o3, o4;

    @BeforeEach
    void setup(){}
}
