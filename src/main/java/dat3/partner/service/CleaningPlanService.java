package dat3.partner.service;

import dat3.partner.api.CleaningPlanController;
import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.repository.CleaningPlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CleaningPlanService {
    CleaningPlanRepository cleaningPlanRepository;

    public CleaningPlanService(CleaningPlanRepository cleaningPlanRepository) {
        this.cleaningPlanRepository = cleaningPlanRepository;
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
    //Edit
    //Delete


}
