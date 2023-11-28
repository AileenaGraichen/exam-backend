package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.CleaningPlan;
import dat3.partner.entity.Unit;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CleaningPlanResponse {
    private int id;
    private int unitId;
    private String userName;
    private LocalDate date;

    public CleaningPlanResponse(CleaningPlan cleaningPlan) {
        this.id = cleaningPlan.getId();
        this.unitId = cleaningPlan.getUnit().getId();
        this.userName = cleaningPlan.getUser().getUsername();
        this.date = cleaningPlan.getDate();
    }
}
