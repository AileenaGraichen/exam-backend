package dat3.partner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="unit_number")
    private String unitNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition = "ENUM('AVAILABLE','IN_PROGRESS','UNAVAILABLE')")
    private UnitStatus unitStatus;

    @JsonIgnore
    @ManyToOne
    private Location location;

    private String type;
    private String keyCode;

    @JsonIgnore
    @ManyToOne
    private Owner owner;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    List<CleaningPlan> cleaningPlans;

    public void addCleaningPlans(CleaningPlan cleaningPlan){
        if(cleaningPlans == null){
            cleaningPlans = new ArrayList<>();
        }
        cleaningPlans.add(cleaningPlan);
    }

    public Unit(String unitNumber, UnitStatus status, Location location, Owner owner, String type, String keyCode) {
        //Add extra objects to constructor when implementing other entities
        this.unitNumber = unitNumber;
        this.unitStatus = status;
        this.location = location;
        this.owner = owner;
        this.type = type;
        this.keyCode = keyCode;
        owner.addUnit(this);
    }


}

  /*

    @OneToMany
    List<UnitTask> unitTasks;

    @OneToMany
    List<MaintenanceTask> maintenanceTasks;

    Create joint table with cleanplans.
    List<CleaningPlan> cleanPlans;

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
