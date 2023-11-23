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
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UnitInfo> unitInfoList;

    public void addUnitInfo(UnitInfo unitInfo){
        if(unitInfoList == null){
            unitInfoList = new ArrayList<>();
        }
        unitInfoList.add(unitInfo);
    }
}
