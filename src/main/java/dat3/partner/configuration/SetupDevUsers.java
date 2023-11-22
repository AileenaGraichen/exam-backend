package dat3.partner.configuration;

import dat3.partner.entity.Location;
import dat3.partner.repository.LocationRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import dat3.security.repository.UserWithRolesRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SetupDevUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    LocationRepository locationRepository;
    PasswordEncoder passwordEncoder;
    String passwordUsedByAll;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.passwordEncoder = passwordEncoder;
        passwordUsedByAll = "test12";
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        setupLocations();
        setupUserWithRoleUsers();
    }


    private void setupLocations(){
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("DueOdde", "BonBonLandsvej"));
        locations.add(new Location("Bornholm Art Museum", "Gudhjemvej, 25, 3760 Gudhjem"));
        locations.add(new Location("Dueodde Beach", "Dueoddevej, 3730 Nexø"));
        locations.add(new Location("Helligdomsklipperne", "Rø Plantagevej, 3770 Allinge"));
        locations.add(new Location("Osterlars Church", "Rønnevej, 12, 3760 Gudhjem"));
        locations.add(new Location("Hammerknuden, Vang Granite Quarry", "Kystvejen, 3770 Allinge"));
        locations.add(new Location("Ekkodalen", "Ekkodalsvej, 3770 Allinge"));
        locations.add(new Location("Almindingen Forest", "Almindingen, 3770 Allinge"));
        locations.add(new Location("Nexø Old Smokehouse", "Sdr. Strandvej, 3730 Nexø"));
        locations.add(new Location("Hammershus Castle", "Hammershusvej, 3, 3770 Allinge"));
        locationRepository.saveAll(locations);
    }



     /*****************************************************************************************
     IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
     iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO

     If you see the lines below in log-outputs on Azure, forget whatever had your attention on, AND FIX THIS PROBLEM

     *****************************************************************************************/
    private void setupUserWithRoleUsers() {
        System.out.println("******************************************************************************");
        System.out.println("********** IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ************");
        System.out.println();
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println();
        System.out.println("******************************************************************************");
        UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(Role.CLEAN);
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.TECH);
        user3.addRole(Role.ADMIN);
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }
}
