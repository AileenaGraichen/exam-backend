package dat3.partner.service;

import dat3.partner.dto.LocationResponse;
import dat3.partner.dto.MaintenanceTaskRequest;
import dat3.partner.dto.MaintenanceTaskResponse;
import dat3.partner.entity.MaintenanceTask;
import dat3.partner.entity.Unit;
import dat3.partner.repository.MaintenanceTaskRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MaintenanceTaskService {

    MaintenanceTaskRepository repository;
    UserWithRolesRepository userWithRolesRepository;
    UnitRepository unitRepository;

    public MaintenanceTaskService(MaintenanceTaskRepository repository, UserWithRolesRepository userWithRolesRepository, UnitRepository unitRepository) {
        this.repository = repository;
        this.userWithRolesRepository = userWithRolesRepository;
        this.unitRepository = unitRepository;
    }

    public Page<MaintenanceTaskResponse> getAllTasks(Pageable pageable){
        Page<MaintenanceTask> tasks = repository.findAll(pageable);
        return tasks.map(task -> new MaintenanceTaskResponse(task));
    }

    public MaintenanceTaskResponse getTaskById(int id){
        MaintenanceTask task = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No task with this id found"));
        return new MaintenanceTaskResponse(task);
    }

    public List<MaintenanceTaskResponse> getTasksByLocationId(int id){
        List<MaintenanceTask> tasks = repository.findTasksByLocationId(id);
        return tasks.stream().map(task -> new MaintenanceTaskResponse(task)).toList();
    }

    public MaintenanceTaskResponse createMaintenanceTask(MaintenanceTaskRequest body){
        UserWithRoles account = null;
        if(body.getAccountUsername() != null) {
            account = userWithRolesRepository.findById(body.getAccountUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No account with this id found"));
        }
        Unit unit = unitRepository.findById(body.getUnitId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No unit with this id found"));

        // Explicitly handle the case where account is null
        MaintenanceTask task;
        if (account != null) {
            task = new MaintenanceTask(body.getDescription(),
                    body.getTitle(),
                    body.getStatus(),
                    body.getPriority(),
                    account,
                    unit,
                    body.getImage());
        } else {
            task = new MaintenanceTask(body.getDescription(),
                    body.getTitle(),
                    body.getStatus(),
                    body.getPriority(),
                    unit,
                    body.getImage());
        }
        return new MaintenanceTaskResponse(repository.save(task));
    }

    public MaintenanceTaskResponse assignUserToTask(int taskId, String username) {
        MaintenanceTask task = repository.findById(taskId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No task with this id found"));
        UserWithRoles account = userWithRolesRepository.findById(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No account with this id found"));

        task.setAssignedUser(account);
        account.getMaintenanceTasks().add(task);

        return new MaintenanceTaskResponse(repository.save(task));
    }
}
