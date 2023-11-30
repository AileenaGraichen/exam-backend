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

    public MaintenanceTaskResponse createMaintenanceTask(MaintenanceTaskRequest body){
        System.out.println(body.getAccountUsername());
        UserWithRoles account = userWithRolesRepository.findById(body.getAccountUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No account with this id found"));
        Unit unit = unitRepository.findById(body.getUnitId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No unit with this id found"));
        MaintenanceTask task = new MaintenanceTask(body.getDescription(),
                body.getTitle(),
                body.getStatus(),
                body.getPriority(),
                account,
                unit,
                body.getImage());
        return new MaintenanceTaskResponse(repository.save(task));
    }
}
