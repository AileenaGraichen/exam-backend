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
    int id;

    @Column(name="location_name")
    String locationName;

    String address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Unit> units;

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
