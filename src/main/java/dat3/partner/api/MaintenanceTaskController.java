package dat3.partner.api;

import dat3.partner.dto.MaintenanceTaskResponse;
import dat3.partner.entity.MaintenanceTask;
import dat3.partner.service.MaintenanceTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-task")
public class MaintenanceTaskController {

    MaintenanceTaskService service;

    public MaintenanceTaskController(MaintenanceTaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<MaintenanceTaskResponse> getAllTasks(){
        return service.getAllTasks();
    }
}
