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

    /*@PostMapping
    public MaintenanceTaskResponse createMaintenanceTask(@RequestBody MaintenanceTaskRequest body){
        System.out.println(body.getImage());
        return service.createMaintenanceTask(body);
    }*/
    @PostMapping()
    public MaintenanceTaskResponse createMaintenanceTask(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("status") String status,
            @RequestParam("priority") String priority,
            @RequestParam("image") MultipartFile image,
            @RequestParam("accountUsername") String accountUsername,
            @RequestParam("unitId") int unitId) throws IOException {

        System.out.println(image);
        MaintenanceTaskRequest body = new MaintenanceTaskRequest(description, title, MaintenanceStatus.valueOf(status), MaintenancePriority.valueOf(priority), image.getBytes(), accountUsername, unitId);
        return service.createMaintenanceTask(body);
    }
}
