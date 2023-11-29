package dat3.partner.service;

import dat3.partner.api.CleaningPlanController;
import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.dto.UnitResponse;
import dat3.partner.entity.CleaningPlan;
import dat3.partner.entity.Unit;
import dat3.partner.repository.CleaningPlanRepository;
import dat3.partner.repository.UnitRepository;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CleaningPlanService {
    CleaningPlanRepository cleaningPlanRepository;
    UnitRepository unitRepository;
    UserWithRolesRepository userWithRolesRepository;

    public CleaningPlanService(CleaningPlanRepository cleaningPlanRepository, UnitRepository unitRepository, UserWithRolesRepository userWithRolesRepository)
    {
        this.cleaningPlanRepository = cleaningPlanRepository;
        this.unitRepository = unitRepository;
        this.userWithRolesRepository = userWithRolesRepository;
    }

    public List<CleaningPlanResponse> getAllPlans() {
        return cleaningPlanRepository.findAll().stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }
    public List<CleaningPlanResponse> getByUserAndDate(String username, LocalDate date) {
        if(!cleaningPlanRepository.existsCleaningPlanByDateAndUser_Username(date, username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cleaning plan for that user on that date, does not exist");
        }
        return cleaningPlanRepository.getCleaningPlansByDateAndUser_Username(date, username).stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }
    public List<CleaningPlanResponse> getByUser(String username) {
        if(!cleaningPlanRepository.existsCleaningPlanByUser_Username( username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cleaning plans for that user does not exist");
        }
        return cleaningPlanRepository.getCleaningPlansByUser_Username(username).stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }
    public List<CleaningPlanResponse> getByUnit(int unitId) {
        if(!cleaningPlanRepository.existsCleaningPlanByUnit_Id(unitId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cleaning plan on that unit does not exist");
        }
        return cleaningPlanRepository.getCleaningPlansByUnit_Id(unitId).stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }

    public CleaningPlanResponse addCleaningPlan(List<CleaningPlanRequest> body) {
        for(CleaningPlanRequest cp : body){
            if(!cleaningPlanRepository.existsCleaningPlanByDateAndUnit_IdAndUser_Username(cp.getDate(), cp.getUnitId(), cp.getUserName())){
                Unit newUnit = unitRepository.findById(cp.getUnitId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Unit not found"));
                UserWithRoles newUser = userWithRolesRepository.findById(cp.getUserName()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
                cleaningPlanRepository.save(new CleaningPlan(cp.getDate(), newUnit, newUser));
            }

        }


    }

//    public CleaningPlanResponse addCleaningPlan(CleaningPlanRequest body) {
//        if(cleaningPlanRepository.existsCleaningPlanByDateAndUnit_IdAndUser_Username(body.getDate(), body.getUnitId(), body.getUserName())){
//
//        }
//
//    }


    //GetAll
    //get by username and date
    //get by username
    //get by unit id

    //Add

    //Delete


}
