package dat3.partner.repository;

import dat3.partner.entity.MaintenanceTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Integer> {
    Page<MaintenanceTask> findAll(Pageable pageable);
}
