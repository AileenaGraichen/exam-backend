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

    LocalDate date;

    @ManyToOne
    UserWithRoles user;



    public CleaningPlan(LocalDate date, UserWithRoles user) {
        this.date = date;
        this.user = user;
    }

}
