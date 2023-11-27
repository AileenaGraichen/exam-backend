package dat3.partner.configuration;

import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.entity.UnitStatus;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
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
    OwnerRepository ownerRepository;
    UnitRepository unitRepository;
    PasswordEncoder passwordEncoder;
    String passwordUsedByAll;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository, UnitRepository unitRepository, OwnerRepository ownerRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.passwordEncoder = passwordEncoder;
        passwordUsedByAll = "test12";
        this.locationRepository = locationRepository;
        this.unitRepository = unitRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        setupTestData();
        setupUserWithRoleUsers();
    }


    private void setupTestData(){
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

        List<Owner> owners = new ArrayList<>();
        owners.add(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        owners.add(new Owner("Jane", "Smith", "jane.smith@example.com", "9876543210"));
        owners.add(new Owner("Alice", "Johnson", "alice.johnson@example.com", "5678901234"));
        owners.add(new Owner("Bob", "Williams", "bob.williams@example.com", "6789012345"));
        owners.add(new Owner("Eva", "Brown", "eva.brown@example.com", "8901234567"));
        owners.add(new Owner("David", "Miller", "david.miller@example.com", "3456789012"));
        ownerRepository.saveAll(owners);


        List<Unit> units = new ArrayList<>();
        units.add(new Unit("U001", UnitStatus.AVAILABLE, locationRepository.findById(1).get(), ownerRepository.findById(1).get(), "Type1", "KeyCode1"));
        units.add( new Unit("U002", UnitStatus.IN_PROGRESS, locationRepository.findById(2).get(), ownerRepository.findById(2).get(), "Type2", "KeyCode2"));
        units.add( new Unit("U003", UnitStatus.UNAVAILABLE, locationRepository.findById(3).get(), ownerRepository.findById(4).get(), "Type3", "KeyCode3"));
        units.add( new Unit("U004", UnitStatus.AVAILABLE, locationRepository.findById(4).get(), ownerRepository.findById(3).get(), "Type4", "KeyCode4"));
        units.add( new Unit("U005", UnitStatus.IN_PROGRESS, locationRepository.findById(5).get(), ownerRepository.findById(5).get(), "Type5", "KeyCode5"));
        units.add( new Unit("U006", UnitStatus.UNAVAILABLE, locationRepository.findById(6).get(), ownerRepository.findById(6).get(), "Type6", "KeyCode6"));
        units.add( new Unit("U007", UnitStatus.AVAILABLE, locationRepository.findById(2).get(), ownerRepository.findById(4).get(), "Type7", "KeyCode7"));
        units.add( new Unit("U008", UnitStatus.IN_PROGRESS, locationRepository.findById(1).get(), ownerRepository.findById(3).get(), "Type8", "KeyCode8"));
        units.add( new Unit("U009", UnitStatus.UNAVAILABLE, locationRepository.findById(1).get(), ownerRepository.findById(1).get(), "Type9", "KeyCode9"));
        units.add( new Unit("U010", UnitStatus.AVAILABLE, locationRepository.findById(7).get(), ownerRepository.findById(3).get(), "Type10", "KeyCode10"));
        unitRepository.saveAll(units);

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
