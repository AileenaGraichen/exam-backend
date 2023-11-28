package dat3.security.service;

import dat3.security.TestUtils;
import dat3.security.dto.UserWithRolesRequest;
import dat3.security.dto.UserWithRolesResponse;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

//You can enable/disable these tests in you maven builds via the maven-surefire-plugin, in your pom-file
@Tag("DisabledSecurityTest")
@DataJpaTest
class UserWithRolesServiceTest {

  UserWithRolesService userWithRolesService;

  @Autowired
  UserWithRolesRepository userWithRolesRepository;

  private boolean dataInitialized = false;

  @BeforeEach
  void setUp() {
    userWithRolesService = new UserWithRolesService(userWithRolesRepository);
    if(!dataInitialized) {
      userWithRolesRepository.deleteAll();
      TestUtils.setupTestUsers(userWithRolesRepository);
      dataInitialized = true;
    }
  }


  @Test
  void getUserWithRoles() {
    UserWithRolesResponse user = userWithRolesService.getUserWithRoles("u1");
    assertEquals(3, user.getRoleNames().size());
    assertTrue(user.getRoleNames().contains("CLEAN"));
    assertTrue(user.getRoleNames().contains("ADMIN"));
    assertTrue(user.getRoleNames().contains("TECH"));
  }

  @Test
  void addRole() {
    UserWithRolesResponse user = userWithRolesService.addRole("u5", Role.TECH);
    assertEquals(1, user.getRoleNames().size());
    assertTrue(user.getRoleNames().contains("TECH"));
  }

  @Test
  void removeRole() {
    UserWithRolesResponse user = userWithRolesService.removeRole("u1", Role.CLEAN);
    assertEquals(2, user.getRoleNames().size());
    assertTrue(user.getRoleNames().contains("ADMIN"));
    assertTrue(user.getRoleNames().contains("TECH"));
    user = userWithRolesService.removeRole("u1", Role.TECH);
    assertEquals(1, user.getRoleNames().size());
  }

  @Test
  void editUserEmail() {
    String originalEmail = userWithRolesRepository.findById("u1").get().getEmail();
    UserWithRolesRequest user1 = new UserWithRolesRequest("newUsername", "newMail@a.dk", "newPassword");
    UserWithRolesResponse user = userWithRolesService.editUserWithRoles("u1",user1);
    assertEquals("u1", user.getUserName());  //IMPORTANT: The username should not be changed
    assertEquals("newMail@a.dk", user.getEmail());
    UserWithRoles editedUser = userWithRolesRepository.findById("u1").get();
    assertNotEquals(originalEmail, editedUser.getPassword());
  }

  @Test
  void editUserPassword() {
    String originalEmail = userWithRolesRepository.findById("u1").get().getEmail();
    UserWithRolesRequest user1 = new UserWithRolesRequest("newUsername", "newMail@a.dk", "newPassword");
    UserWithRolesResponse user = userWithRolesService.editPasswordUserWithRoles("u1", user1);
    assertEquals("u1", user.getUserName());  //IMPORTANT: The username should not be changed
    assertNotEquals("newMail@a.dk", user.getEmail()); //The email should not be changed
    UserWithRoles editedUser = userWithRolesRepository.findById("u1").get();
    assertNotEquals(originalEmail, editedUser.getPassword());
  }

  @Test
  void addUserWithRolesWithNoRole() {
    UserWithRolesRequest user = new UserWithRolesRequest("u6", "xx@x.dk", "new_Password");
    UserWithRolesResponse newUser = userWithRolesService.addUserWithRoles(user, null);
    assertEquals(0, newUser.getRoleNames().size());
    assertEquals("u6", newUser.getUserName());
    assertEquals("xx@x.dk", newUser.getEmail());
    //Verify that the password is hashed
    assertEquals(60,userWithRolesRepository.findById("u6").get().getPassword().length());
  }
  @Test
  void addUserWithRolesWithRole() {
    UserWithRolesRequest user = new UserWithRolesRequest("u6", "xx@x.dk", "new_Password");
    UserWithRolesResponse newUser = userWithRolesService.addUserWithRoles(user, Role.CLEAN);
    assertEquals(1, newUser.getRoleNames().size());
    assertTrue(newUser.getRoleNames().contains("CLEAN"));
    assertEquals("u6", newUser.getUserName());
    assertEquals("xx@x.dk", newUser.getEmail());
  }

}