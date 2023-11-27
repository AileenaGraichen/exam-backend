package dat3.security.api;

import dat3.security.dto.UserWithRolesRequest;
import dat3.security.dto.UserWithRolesResponse;
import dat3.security.entity.Role;
import dat3.security.service.UserWithRolesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-with-role")
public class UserWithRoleController {

  //Take care with this. If no role is required for new users, add null as the value below
  static Role DEFAULT_ROLE_TO_ASSIGN = null;

  UserWithRolesService userWithRolesService;

  public UserWithRoleController(UserWithRolesService userWithRolesService) {
    this.userWithRolesService = userWithRolesService;
  }

  @GetMapping
  public List<UserWithRolesResponse> getUsers(){
    return userWithRolesService.getUsers();
  }

  @GetMapping("/{username}")
  public UserWithRolesResponse getUserByUsername(@PathVariable String username){
    return userWithRolesService.getUserWithRoles(username);
  }

  @PatchMapping("/update/{username}")
  public UserWithRolesResponse updateUser(@PathVariable String username, @RequestBody UserWithRolesRequest request){
    return userWithRolesService.editUserWithRoles(username, request);
  }

  @PatchMapping("/update-password/{username}")
  public UserWithRolesResponse updateUserPassword(@PathVariable String username, @RequestBody UserWithRolesRequest request){
    return userWithRolesService.editPasswordUserWithRoles(username, request);
  }

  //ADMIN users can call this. Set DEFAULT_ROLE_TO_ASSIGN to null if no role should be added
  @PostMapping
  public UserWithRolesResponse addUserWithRoles(@RequestBody UserWithRolesRequest request) {
    return userWithRolesService.addUserWithRoles (request, DEFAULT_ROLE_TO_ASSIGN);
  }

  //Take care with this. This should NOT be something everyone can call
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/add-role/{username}/{role}")
  public UserWithRolesResponse addRole(@PathVariable String username, @PathVariable String role) {
    return userWithRolesService.addRole(username, Role.fromString(role));
  }
  //Take care with this. This should NOT be something everyone can call
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/remove-role/{username}/{role}")
  public UserWithRolesResponse removeRole(@PathVariable String username, @PathVariable String role) {
    return userWithRolesService.removeRole(username, Role.fromString(role));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/delete-user/{username}")
  public void deleteUser(@PathVariable String username){
    userWithRolesService.deleteUser(username);
  }
}
