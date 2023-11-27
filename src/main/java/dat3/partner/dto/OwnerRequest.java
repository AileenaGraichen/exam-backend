package dat3.partner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
}
