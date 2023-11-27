package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.partner.entity.Location;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse {
    private int id;
    private String locationName;
    private String address;

    public LocationResponse(Location location) {
        this.id = location.getId();
        this.locationName = location.getLocationName();
        this.address = location.getAddress();
    }
}
