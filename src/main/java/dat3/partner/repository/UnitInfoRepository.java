package dat3.partner.repository;

import dat3.partner.entity.UnitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitInfoRepository extends JpaRepository<UnitInfo, Integer> {
   UnitInfo getUnitInfoByUnitId(int id);
}
