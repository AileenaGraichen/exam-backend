package dat3.partner.api;

import dat3.partner.dto.LocationResponse;
import dat3.partner.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    LocationService locationService;

    public LocationController(LocationService locationService)
    {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationResponse> getAllLocations(){
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public LocationResponse getOneLocation(@PathVariable int id){
        return locationService.getOneLocation(id);
    }


}
