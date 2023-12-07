package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.*;
import lombok.*;
import org.apache.tika.Tika;

import java.util.Base64;
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
    private String image;
    private String MIMEType;

    List<CleaningPlanResponse> cleaningPlans;
    List<MaintenanceTaskResponse> maintenanceTasks;


    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.unitNumber = unit.getUnitNumber();
        this.status = unit.getUnitStatus();
        this.location = new LocationResponse(unit.getLocation());
        this.type = unit.getType();
        this.keyCode = unit.getKeyCode();
        this.ownerId = unit.getOwner().getId();
        if (unit.getImage() != null && unit.getImage().length > 0) {
            this.image = Base64.getEncoder().encodeToString(unit.getImage());
            this.MIMEType = new Tika().detect(unit.getImage());
        }
        if(unit.getCleaningPlans() != null) {
            this.cleaningPlans = unit.getCleaningPlans().stream().map(plan -> new CleaningPlanResponse(plan)).toList();
        }
        if(unit.getMaintenanceTasks() != null) {
            this.maintenanceTasks = unit.getMaintenanceTasks().stream().map(task -> new MaintenanceTaskResponse(task)).toList();
        }
    }
}
