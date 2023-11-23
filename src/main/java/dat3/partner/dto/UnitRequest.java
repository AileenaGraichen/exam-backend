package dat3.partner.dto;

import dat3.partner.entity.UnitStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitRequest {
    private String unitNumber;
    private UnitStatus unitStatus;
    private int locationId;
    private String type;
    private String keyCode;
    private int ownerId;
}

