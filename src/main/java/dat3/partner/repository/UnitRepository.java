package dat3.partner.repository;

import dat3.partner.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    Page<Unit> getUnitsByLocationId(int id);
}
