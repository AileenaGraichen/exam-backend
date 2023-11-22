package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.Location;
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
    private int unitNumber;
    private UnitStatus status;
    private Location location;

    //UnitInfo
    //List<UnitTasks>
    //List<MaintenanceTasks>

    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.unitNumber = unit.getUnitNumber();
        this.status = unit.getUnitStatus();
        this.location = unit.getLocation();
        //Different lists added here too.
    }
}
