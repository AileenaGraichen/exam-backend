package dat3.partner.repository;

import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.entity.CleaningPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CleaningPlanRepository extends JpaRepository<CleaningPlan, Integer> {
    boolean existsCleaningPlanByDateAndUser_Username(LocalDate date, String username);
    List<CleaningPlan> getCleaningPlansByDateAndUser_Username(LocalDate date, String username);

    boolean existsCleaningPlanByUser_Username(String username);
    List<CleaningPlan> getCleaningPlansByUser_Username(String username);
    boolean existsCleaningPlanByUnit_Id(int unitId);
    List<CleaningPlan> getCleaningPlansByUnit_Id(int unitId);
    boolean existsCleaningPlanByDateAndUnit_IdAndUser_Username(LocalDate date, int unitId, String username);
    CleaningPlan getCleaningPlanByDateAndUser_UsernameAndUnitId(LocalDate date, String username, int unitId);
}
