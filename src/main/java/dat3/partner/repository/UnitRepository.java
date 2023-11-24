package dat3.partner.repository;

import dat3.partner.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    Page<Unit> getUnitsByLocationId(Pageable pageable, int id);

    Page<Unit> getUnitByOwnerId(Pageable pageable, int id);
    Page<Unit> findUnitsByType(Pageable pageable, String type);

    boolean existsByLocationIdAndAndUnitNumber(int locationId, String number);
    boolean existsByLocationId(int locationId);
}
