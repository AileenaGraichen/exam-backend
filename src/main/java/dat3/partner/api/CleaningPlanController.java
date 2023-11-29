package dat3.partner.api;


import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.service.CleaningPlanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cleaning")
public class CleaningPlanController {
    CleaningPlanService service;

    public CleaningPlanController(CleaningPlanService service) {
        this.service = service;
    }

    //GetAll
    @GetMapping
    public List<CleaningPlanResponse> getAllPlans(){
        return service.getAllPlans();
    }
    //get by username
    @GetMapping("/user/{username}")
    public List<CleaningPlanResponse> getByUser(@PathVariable String username){
        return service.getByUser(username);
    }
    //get by unit id
    @GetMapping("/unit/{unitId}")
    public List<CleaningPlanResponse> getByUnit(@PathVariable int unitId){
        return service.getByUnit(unitId);
    }

    //Get by username and date
    @GetMapping("/{username}/{date}")
    public List<CleaningPlanResponse> getByUserAndDate(@PathVariable String username, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date){
        return service.getByUserAndDate(username, date);
    }

    //Get all by date too? Hvis de gerne vil slå alt rengøring op for idag fx. Og ikke kun på specifikke brugere.

    @PostMapping
    public CleaningPlanResponse addCleaningPlan(@RequestBody List<CleaningPlanRequest> body){
        return service.addCleaningPlan(body);
    }
    //Add
    //edit
    //delete
}
