package dat3.partner.service;

import dat3.partner.dto.LocationResponse;
import dat3.partner.entity.Location;
import dat3.partner.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LocationService {
    LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    public List<LocationResponse> getAllLocations(){
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(location -> new LocationResponse(location)).toList();
    }

    public LocationResponse getOneLocation(int id) {
        Location location = locationRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Location not found"));
        return new LocationResponse(location);
    }

    //Add location


    //Edit location
    //Delete location (if empty)




}
