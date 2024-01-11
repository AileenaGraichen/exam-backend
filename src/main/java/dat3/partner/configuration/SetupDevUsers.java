package dat3.partner.configuration;

import dat3.partner.entity.*;
import dat3.partner.repository.CleaningPlanRepository;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.MaintenanceTaskRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import dat3.security.repository.UserWithRolesRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SetupDevUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    LocationRepository locationRepository;
    OwnerRepository ownerRepository;
    UnitRepository unitRepository;
    MaintenanceTaskRepository maintenanceTaskRepository;
    CleaningPlanRepository cleaningPlanRepository;
    PasswordEncoder passwordEncoder;
    String passwordUsedByAll;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository, PasswordEncoder passwordEncoder, LocationRepository locationRepository, UnitRepository unitRepository, OwnerRepository ownerRepository, CleaningPlanRepository cleaningPlanRepository, MaintenanceTaskRepository maintenanceTaskRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.passwordEncoder = passwordEncoder;
        passwordUsedByAll = "test12";
        this.locationRepository = locationRepository;
        this.unitRepository = unitRepository;
        this.ownerRepository = ownerRepository;
        this.cleaningPlanRepository = cleaningPlanRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        //setupUserWithRoleUsers();
        //setupTestData();
    }


    private void setupTestData(){
        Location location1 = locationRepository.save(new Location("Dueodde Badehotel", "BonBonLandsvej"));
        Location location2 = locationRepository.save(new Location("Østersøen", "Gudhjemvej, 25, 3760 Gudhjem"));
        Location location3 = locationRepository.save(new Location("Balka Ferielejligheder", "Dueoddevej, 3730 Nexø"));
        Location location4 = locationRepository.save(new Location("Årsdale Søpark", "Rø Plantagevej, 3770 Allinge"));
        Location location5 = locationRepository.save(new Location("Boderne Nr. 1", "Rønnevej, 12, 3760 Gudhjem"));
        Location location6 = locationRepository.save(new Location("Bageriet", "Kystvejen, 3770 Allinge"));
        Location location7 = locationRepository.save(new Location("Møllegården", "Ekkodalsvej, 3770 Allinge"));
        Location location8 = locationRepository.save(new Location("Munken", "Almindingen, 3770 Allinge"));
        Location location9 = locationRepository.save(new Location("Byfogedhuset", "Sdr. Strandvej, 3730 Nexø"));
        Location location10 = locationRepository.save(new Location("Svaneke Sommerhuse", "Hammershusvej, 3, 3770 Allinge"));

        List<Owner> owners = new ArrayList<>();
        Owner owner1 = ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "1234567890"));
        Owner owner2 = ownerRepository.save(new Owner("Jane", "Smith", "jane.smith@example.com", "9876543210"));
        Owner owner3 = ownerRepository.save(new Owner("Alice", "Johnson", "alice.johnson@example.com", "5678901234"));
        Owner owner4 = ownerRepository.save(new Owner("Bob", "Williams", "bob.williams@example.com", "6789012345"));
        Owner owner5 = ownerRepository.save(new Owner("Eva", "Brown", "eva.brown@example.com", "8901234567"));
        Owner owner6 = ownerRepository.save(new Owner("David", "Miller", "david.miller@example.com", "3456789012"));


        List<Unit> units = new ArrayList<>();
        Unit unit1 = unitRepository.save(new Unit("101", UnitStatus.AVAILABLE, location1, owner1, "Lejlighed 2 pers.", "Reception", null));
        Unit unit2 = unitRepository.save( new Unit("115", UnitStatus.IN_PROGRESS, location1, owner2, "Lejlighed 2-4 pers.", "Reception", null));
        Unit unit3 = unitRepository.save( new Unit("7", UnitStatus.UNAVAILABLE, location2, owner3, "4-6 pers. m/havudsigt", "På bordet", null));
        Unit unit4 = unitRepository.save( new Unit("19", UnitStatus.AVAILABLE, location2, owner4, "4-6 pers. m/havudsigt", "På bordet", null));
        Unit unit5 = unitRepository.save( new Unit("3", UnitStatus.IN_PROGRESS, location3, owner6, "Lejlighed 2 pers.", "På bordet", null));
        Unit unit6 = unitRepository.save( new Unit("6", UnitStatus.UNAVAILABLE, location4, owner5, "Standard 2-4 pers.", "På bordet", null));
        Unit unit7 = unitRepository.save( new Unit("9", UnitStatus.AVAILABLE, location4, owner6, "2-4 pers. m/havudsigt", "På bordet", null));
        Unit unit8 = unitRepository.save( new Unit("8", UnitStatus.IN_PROGRESS, location5, owner2, "Lejlighed 2 pers. panorama", "3715", null));
        Unit unit9 = unitRepository.save( new Unit("10", UnitStatus.UNAVAILABLE, location5, owner4, "Lejlighed 2 pers. Standard", "3748", null));
        Unit unit10 = unitRepository.save( new Unit("8", UnitStatus.AVAILABLE, location8, owner1, "2-4 pers. - 58m2", "På bordet", null));



        /*List<MaintenanceTask> tasks = new ArrayList<>();
        tasks.add(new MaintenanceTask("Vindue i badeværelse revnet", "Vindue revnet", MaintenanceStatus.NOT_STARTED, MaintenancePriority.MEDIUM, userWithRolesRepository.findById("user1").get(), unit1, null));
        tasks.add(new MaintenanceTask("Spisebordsstol er væk", "Mangler stol", MaintenanceStatus.IN_PROGRESS, MaintenancePriority.HIGH, userWithRolesRepository.findById("user2").get(), unit2, null));
        tasks.add(new MaintenanceTask("Bordben knækket på sofabord", "Bordben knækket", MaintenanceStatus.DONE, MaintenancePriority.HIGH, userWithRolesRepository.findById("user3").get(), unit3, null));
        tasks.add(new MaintenanceTask("Vandhane i køkkenet drypper", "Vandhane drypper", MaintenanceStatus.NOT_STARTED, MaintenancePriority.MEDIUM, userWithRolesRepository.findById("user4").get(), unit4, null));
        tasks.add(new MaintenanceTask("Toilettet kan ikke skylde ud", "Stoppet toilet", MaintenanceStatus.IN_PROGRESS, MaintenancePriority.HIGH, userWithRolesRepository.findById("user1").get(), unit5, null));
        tasks.add(new MaintenanceTask("Toiletbræt trænger til udskriftning", "Toiletbræt", MaintenanceStatus.DONE, MaintenancePriority.LOW, userWithRolesRepository.findById("user2").get(), unit6, null));
        tasks.add(new MaintenanceTask("Stor plet på højre side af sofa", "Plet på sofa", MaintenanceStatus.NOT_STARTED, MaintenancePriority.LOW, userWithRolesRepository.findById("user3").get(), unit7, null));
        tasks.add(new MaintenanceTask("Der er kun en kop", "Manger service", MaintenanceStatus.IN_PROGRESS, MaintenancePriority.MEDIUM, userWithRolesRepository.findById("user4").get(), unit8, null));
        tasks.add(new MaintenanceTask("Døren til soveværelse er svær at lukke", "Dør binder", MaintenanceStatus.DONE, MaintenancePriority.LOW, userWithRolesRepository.findById("user1").get(), unit9, null));
        tasks.add(new MaintenanceTask("Manglende internet forbindelse", "Internet forbindelse", MaintenanceStatus.NOT_STARTED, MaintenancePriority.HIGH, userWithRolesRepository.findById("user2").get(), unit10, null));
        maintenanceTaskRepository.saveAll(tasks);*/

        /*List<CleaningPlan> plans = new ArrayList<>();
        plans.add(new CleaningPlan(LocalDate.now(), unit1, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now(), unit2, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now(), unit3, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now(), unit4, userWithRolesRepository.findById("user1").get()));

        plans.add(new CleaningPlan(LocalDate.now().plusDays(2), unit2, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(2), unit3, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(2), unit4, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(2), unit5, userWithRolesRepository.findById("user3").get()));

        plans.add(new CleaningPlan(LocalDate.now().plusDays(4), unit2, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(4), unit5, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(4), unit6, userWithRolesRepository.findById("user1").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(4), unit7, userWithRolesRepository.findById("user1").get()));

        plans.add(new CleaningPlan(LocalDate.now().plusDays(6), unit4, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(6), unit5, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(6), unit6, userWithRolesRepository.findById("user3").get()));
        plans.add(new CleaningPlan(LocalDate.now().plusDays(6), unit7, userWithRolesRepository.findById("user3").get()));
        cleaningPlanRepository.saveAll(plans);
*/
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

        UserWithRoles user1 = new UserWithRoles("partneroe", "securepassword", "partner@kea.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.TECH);
        user3.addRole(Role.ADMIN);
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }
}
