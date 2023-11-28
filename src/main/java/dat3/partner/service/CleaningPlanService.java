package dat3.partner.service;

import dat3.partner.api.CleaningPlanController;
import dat3.partner.dto.CleaningPlanRequest;
import dat3.partner.dto.CleaningPlanResponse;
import dat3.partner.repository.CleaningPlanRepository;
import org.springframework.stereotype.Service;

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
        return cleaningPlanRepository.getCleaningPlansByDateAndUser_Username(date, username).stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }
    public List<CleaningPlanResponse> getByUser(String username) {
        return cleaningPlanRepository.getCleaningPlansByUser_Username(username).stream().map(cleaningPlan -> new CleaningPlanResponse(cleaningPlan)).toList();
    }
    public List<CleaningPlanResponse> getByUnit(int unitId) {
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
