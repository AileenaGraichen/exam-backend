package dat3.partner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="unit_number")
    private int unitNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition = "ENUM('AVAILABLE','IN_PROGRESS','UNAVAILABLE')")
    private UnitStatus unitStatus;

    @ManyToOne
    private Location location;

    /*
    @OneToOne
    UnitInfo

    @OneToMany
    List<UnitTask> unitTasks;

    @OneToMany
    List<MaintenanceTask> maintenanceTasks;

    public void addUnitTask(UnitTask unitTask){
        if(unitTasks == null){
            unitTasks = new ArrayList<>();
        }
        unitTasks.add(unitTask);
    }
    public void addMaintenanceTask(MaintenanceTask mTask){
        if(maintenanceTasks == null){
            maintenanceTasks = new ArrayList<>();
        }
        maintenanceTasks.add(mTask);
    }
    */

    public Unit(int unitNumber, UnitStatus status, Location location)
    {
        //Add extra objects to constructor when implementing other entities
        this.unitNumber = unitNumber;
        this.unitStatus = status;
        this.location = location;
        location.addUnit(this);
    }


}
