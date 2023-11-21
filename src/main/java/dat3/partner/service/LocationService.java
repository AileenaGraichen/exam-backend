package dat3.partner.service;

import dat3.partner.dto.LocationRequest;
import dat3.partner.dto.LocationResponse;
import dat3.partner.entity.Location;
import dat3.partner.repository.LocationRepository;
import dat3.security.dto.LoginRequest;
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
    public LocationResponse addLocation(LocationRequest body){
        if(locationRepository.existsLocationByLocationName(body.getLocationName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location with that name already exists");
        }
        Location newLocation = new Location();
        newLocation.setLocationName(body.getLocationName());
        newLocation.setAddress(body.getAddress());
        //Add List of units that belong to this location here?? Or does nothing belong to location yet

        newLocation = locationRepository.save(newLocation);
        return new LocationResponse(newLocation);
    }

    public LocationResponse editLocation(int id, LocationRequest body) {
        if(!locationRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with that id does not exist");
        }
        Location editLocation = new Location(id, body.getLocationName(), body.getAddress());
        editLocation = locationRepository.save(editLocation);

        return new LocationResponse(editLocation);
    }

    //TODO Delete location (if empty)
    public void deleteLocation(int id) {
        //Do this method when unit entity backend is built up
        //Need UnitRepository to check if any of them are
        //connected to the locationID before deleting
    }








}
