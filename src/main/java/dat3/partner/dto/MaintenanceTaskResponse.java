package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.MaintenancePriority;
import dat3.partner.entity.MaintenanceStatus;
import dat3.partner.entity.MaintenanceTask;
import dat3.security.dto.UserWithRolesResponse;
import lombok.*;
import org.apache.tika.Tika;

import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaintenanceTaskResponse {
    private int id;
    private String description;
    private String title;
    private MaintenanceStatus status;
    private MaintenancePriority priority;
    private String image;
    private String MIMEType;
    private String accountUsername;
    private int unitId;
    private String unitNumber;
    private LocalDateTime created;
    private LocalDateTime updated;

    public MaintenanceTaskResponse(MaintenanceTask maintenanceTask){
        this.id = maintenanceTask.getId();
        this.description = maintenanceTask.getDescription();
        this.title = maintenanceTask.getTitle();
        this.status = maintenanceTask.getStatus();
        this.priority = maintenanceTask.getPriority();
        if (maintenanceTask.getImage() != null && maintenanceTask.getImage().length > 0) {
            this.image = Base64.getEncoder().encodeToString(maintenanceTask.getImage());
            this.MIMEType = new Tika().detect(maintenanceTask.getImage());
        }
        this.accountUsername = maintenanceTask.getAccount().getUsername();
        this.unitId = maintenanceTask.getUnit().getId();
        this.unitNumber = maintenanceTask.getUnit().getUnitNumber();
        this.created = maintenanceTask.getCreated();
        this.updated = maintenanceTask.getUpdated();
    }
}
