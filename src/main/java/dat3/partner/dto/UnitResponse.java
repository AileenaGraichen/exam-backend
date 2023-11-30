package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnitResponse {

    private int id;
    private String unitNumber;
    private UnitStatus status;
    private LocationResponse location;
    private String type;
    private String keyCode;
    private int ownerId;

    List<CleaningPlanResponse> cleaningPlans;
    List<MaintenanceTaskResponse> maintenanceTasks;
    //List<UnitTasks>
    //List<MaintenanceTasks>

    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.unitNumber = unit.getUnitNumber();
        this.status = unit.getUnitStatus();
        this.location = new LocationResponse(unit.getLocation());
        this.type = unit.getType();
        this.keyCode = unit.getKeyCode();
        this.ownerId = unit.getOwner().getId();
        this.cleaningPlans = unit.getCleaningPlans().stream().map(plan -> new CleaningPlanResponse(plan)).toList();
        this.maintenanceTasks = unit.getMaintenanceTasks().stream().map(task -> new MaintenanceTaskResponse(task)).toList();

        //Different lists added here too.
        //TODO add blob for img
    }
}
