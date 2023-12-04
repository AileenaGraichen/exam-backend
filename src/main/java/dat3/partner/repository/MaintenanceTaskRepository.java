package dat3.partner.repository;

import dat3.partner.entity.MaintenanceTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Integer> {
    Page<MaintenanceTask> findAll(Pageable pageable);

    @Query("SELECT mt FROM MaintenanceTask mt " +
            "JOIN mt.unit u " +
            "JOIN u.location l " +
            "WHERE l.id = :locationId")
    List<MaintenanceTask> findTasksByLocationId(@Param("locationId") int locationId);
}
