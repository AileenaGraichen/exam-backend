package dat3.partner.repository;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UnitRepositoryTest {

    @Autowired
    UnitRepository unitRepository;

    @BeforeEach
    void setup(){

    }


}
