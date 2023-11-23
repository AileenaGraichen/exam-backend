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
    private String unitNumber;
    private UnitStatus status;
    private Location location;



    //List<UnitTasks>
    //List<MaintenanceTasks>
    //List<CleaningTask>

    public UnitResponse(Unit unit) {
        this.id = unit.getId();
        this.unitNumber = unit.getUnitNumber();
        this.status = unit.getUnitStatus();
        this.location = unit.getLocation();
        //Different lists added here too.
    }
}
