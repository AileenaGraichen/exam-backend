package dat3.partner.repository;

import dat3.partner.entity.CleaningPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningPlanRepository extends JpaRepository<CleaningPlan, Integer> {
    //getByUserIdAndDate
    //getByUserId
    //getByUnitId
}
