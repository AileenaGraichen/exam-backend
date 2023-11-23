package dat3.partner.entity;

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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="location_name")
    private String locationName;

    private String address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Unit> units;

    public void addUnit(Unit unit){
        if(units == null){
            units = new ArrayList<>();
        }
        units.add(unit);
    }

    public Location(String locationName, String address)
    {
        this.locationName = locationName;
        this.address = address;
    }
}