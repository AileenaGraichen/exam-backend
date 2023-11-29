package dat3.partner.dto;

import dat3.partner.entity.MaintenancePriority;
import dat3.partner.entity.MaintenanceStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceTaskRequest {
    private String description;
    private String title;
    private MaintenanceStatus status;
    private MaintenancePriority priority;
    private byte[] image;
    private String accountUsername;
    private int unitId;
}
