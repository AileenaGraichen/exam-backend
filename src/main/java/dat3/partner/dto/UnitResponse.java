package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.entity.UnitStatus;
import lombok.*;

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

    //List<UnitTasks>
    //List<MaintenanceTasks>
    //List<CleaningTask>

    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.unitNumber = unit.getUnitNumber();
        this.status = unit.getUnitStatus();
        this.location = new LocationResponse(unit.getLocation());
        this.type = unit.getType();
        this.keyCode = unit.getKeyCode();
        this.ownerId = unit.getOwner().getId();
        //Different lists added here too.
        //TODO Maybe add blob for img
    }
}
