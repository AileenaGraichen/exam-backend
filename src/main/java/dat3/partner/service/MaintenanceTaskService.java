package dat3.partner.service;

import dat3.partner.dto.LocationResponse;
import dat3.partner.dto.MaintenanceTaskResponse;
import dat3.partner.entity.MaintenanceTask;
import dat3.partner.repository.MaintenanceTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceTaskService {

    MaintenanceTaskRepository repository;

    public MaintenanceTaskService(MaintenanceTaskRepository repository) {
        this.repository = repository;
    }

    public List<MaintenanceTaskResponse> getAllTasks(){
        List<MaintenanceTask> tasks = repository.findAll();
        return tasks.stream().map(task -> new MaintenanceTaskResponse(task)).toList();
    }
}
