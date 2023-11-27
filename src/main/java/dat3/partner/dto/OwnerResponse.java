package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private List<UnitResponse> unitList;

    public OwnerResponse(Owner owner){
        this.id = owner.getId();
        this.firstName = owner.getFirstName();
        this.lastName = owner.getLastName();
        this.email = owner.getEmail();
        this.mobile = owner.getMobile();
        this.unitList = owner.getUnitList().stream().map(UnitResponse::new).toList();
    }
}