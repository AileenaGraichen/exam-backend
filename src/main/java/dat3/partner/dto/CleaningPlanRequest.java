package dat3.partner.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CleaningPlanRequest {
    int unitId;
    private String userName;
    LocalDate date;
}
