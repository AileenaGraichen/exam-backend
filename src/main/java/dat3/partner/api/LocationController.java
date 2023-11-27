package dat3.partner.api;

import dat3.partner.dto.LocationRequest;
import dat3.partner.dto.LocationResponse;
import dat3.partner.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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
    public Page<LocationResponse> getAllLocations(Pageable pageable){
        return locationService.getAllLocations(pageable);
    }


    @GetMapping("/{id}")
    public LocationResponse getOneLocation(@PathVariable int id){
        return locationService.getOneLocation(id);
    }

    @PostMapping
    public LocationResponse addLocation(@RequestBody LocationRequest body){
        return locationService.addLocation(body);
    }
    @PatchMapping("/{id}")
    public LocationResponse editLocation(@PathVariable int id, @RequestBody LocationRequest body){
        return locationService.editLocation(id, body);
    }
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable int id){
        locationService.deleteLocation(id);
    }
}
