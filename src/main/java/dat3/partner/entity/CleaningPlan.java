package dat3.partner.entity;

import dat3.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CleaningPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Unit unit;
    @ManyToOne(cascade = CascadeType.DETACH)
    private UserWithRoles user;

    private LocalDate date;

    public CleaningPlan(LocalDate date, Unit unit, UserWithRoles user) {
        this.date = date;
        this.unit = unit;
        this.user = user;
        unit.addCleaningPlans(this);
        user.addCleaningPlans(this);
    }
}
