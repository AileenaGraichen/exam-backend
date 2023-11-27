package dat3.security.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithRolesRequest {
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String password;
}