package dat3.partner.api;

import dat3.partner.dto.MaintenanceTaskRequest;
import dat3.partner.dto.MaintenanceTaskResponse;
import dat3.partner.entity.MaintenancePriority;
import dat3.partner.entity.MaintenanceStatus;
import dat3.partner.entity.MaintenanceTask;
import dat3.partner.service.MaintenanceTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-task")
public class MaintenanceTaskController {

    MaintenanceTaskService service;

    public MaintenanceTaskController(MaintenanceTaskService service) {
        this.service = service;
    }

    @GetMapping
    public Page<MaintenanceTaskResponse> getAllTasks(Pageable pageable){
        return service.getAllTasks(pageable);
    }

    @GetMapping("/location/{locationId}")
    public List<MaintenanceTaskResponse> getByLocationId(@PathVariable int locationId){
        return service.getTasksByLocationId(locationId);
    }

    @PostMapping()
    public MaintenanceTaskResponse createMaintenanceTask(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("status") String status,
            @RequestParam("priority") String priority,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "accountUsername", required = false) String accountUsername,
            @RequestParam("unitId") int unitId) throws IOException {

        byte[] imageByte = image != null ? image.getBytes() : null ;
        MaintenanceTaskRequest body = new MaintenanceTaskRequest(description, title, MaintenanceStatus.valueOf(status), MaintenancePriority.valueOf(priority), imageByte, accountUsername, unitId);
        return service.createMaintenanceTask(body);
    }
}
