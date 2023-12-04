package dat3.security.dto;


import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.dto.MaintenanceTaskResponse;
import lombok.Getter;
import lombok.Setter;
import dat3.security.entity.UserWithRoles;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserWithRolesResponse {
    String userName;
    List<String> roleNames;
    String email;
    List<CleaningPlanResponse> cleaningPlans;
    List<MaintenanceTaskResponse> maintenanceTasks;
    public UserWithRolesResponse(UserWithRoles userWithRoles){
        this.userName = userWithRoles.getUsername();
        this.roleNames = userWithRoles.getRoles().stream().map(role -> role.toString()).collect(Collectors.toList());
        this.email = userWithRoles.getEmail();
        this.cleaningPlans = userWithRoles.getCleaningPlans().stream().map(plan -> new CleaningPlanResponse(plan)).toList();
        //this.maintenanceTasks = userWithRoles.getMaintenanceTasks().stream().map(task -> new MaintenanceTaskResponse(task)).toList();
    }

}
