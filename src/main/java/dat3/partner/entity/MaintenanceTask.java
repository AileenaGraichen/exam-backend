package dat3.partner.entity;

import dat3.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition = "ENUM('DONE','IN_PROGRESS','NOT_STARTED')")
    private MaintenanceStatus status;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @Enumerated(EnumType.STRING)
    @Column(name="priority", columnDefinition = "ENUM('HIGH','MEDIUM','LOW')")
    private MaintenancePriority priority;
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
    @ManyToOne
    private UserWithRoles account;
    @ManyToOne
    private Unit unit;

    public MaintenanceTask(String description, String title, MaintenanceStatus status, MaintenancePriority priority, UserWithRoles account, Unit unit, byte[] image){
        this.description = description;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.image = image;
        this.account = account;
        this.unit = unit;
        account.addMaintenanceTask(this);
        unit.addMaintenanceTask(this);
    }

}
