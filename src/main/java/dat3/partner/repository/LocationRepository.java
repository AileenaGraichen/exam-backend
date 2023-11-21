package dat3.partner.repository;

import dat3.partner.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    boolean existsLocationByLocationName(String name);
}
