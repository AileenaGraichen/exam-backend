package dat3.partner.service;

import dat3.partner.dto.UnitRequest;
import dat3.partner.dto.UnitResponse;
import dat3.partner.entity.Location;
import dat3.partner.entity.Owner;
import dat3.partner.entity.Unit;
import dat3.partner.repository.LocationRepository;
import dat3.partner.repository.OwnerRepository;
import dat3.partner.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitService {
    UnitRepository unitRepository;
    LocationRepository locationRepository;
    OwnerRepository ownerRepository;

    public UnitService(UnitRepository unitRepository, LocationRepository locationRepository, OwnerRepository ownerRepository)
    {
        this.unitRepository = unitRepository;
        this.locationRepository = locationRepository;
        this.ownerRepository = ownerRepository;
    }

    public Page<UnitResponse> getAllUnits(Pageable pageable) {
        /*List<Unit> units = unitRepository.findAll();
        return units.stream().map(unit -> new UnitResponse(unit)).toList();*/
        Page<Unit> units = unitRepository.findAll(pageable);
        return  units.map(unit -> new UnitResponse(unit));
    }

    public Page<UnitResponse> getUnitsByLocationId(Pageable pageable, int id) {
        Page<Unit> units = unitRepository.getUnitsByLocationId(pageable, id);
        return units.map(unit -> new UnitResponse(unit));
    }

    public Page<UnitResponse> getUnitsByType(Pageable pageable, String type) {
        Page<Unit> units = unitRepository.findUnitsByType(pageable, type);
        return units.map(unit -> new UnitResponse(unit));
    }

    public Page<UnitResponse> getUnitsByOwnerId(Pageable pageable, int ownerId){
        Page<Unit> units = unitRepository.getUnitByOwnerId(pageable, ownerId);
        return units.map(unit -> new UnitResponse(unit));
    }

    public UnitResponse addUnit(UnitRequest body) {
        if(unitRepository.existsByLocationIdAndAndUnitNumber(body.getLocationId(), body.getUnitNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit with number "+body.getUnitNumber()+" in that location already exists");
        }
        Location location = locationRepository.findById(body.getLocationId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No location with this id found"));
        Owner owner = ownerRepository.findById(body.getOwnerId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No owner with this id found"));

        Unit newUnit = new Unit(body.getUnitNumber(), body.getUnitStatus(), location, owner, body.getType(), body.getKeyCode());

        return new UnitResponse(unitRepository.save(newUnit));
    }

    public UnitResponse editUnit(int id, UnitRequest body) {
        Unit unit = unitRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No unit with this id found"));
        Owner owner = ownerRepository.findById(body.getOwnerId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No owner with this id found"));
        Location location = locationRepository.findById(body.getLocationId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No location with this id found"));

        unit.setUnitNumber(body.getUnitNumber());
        unit.setType(body.getType());
        unit.setUnitStatus(body.getUnitStatus());
        unit.setKeyCode(body.getKeyCode());
        unit.setOwner(owner);
        unit.setLocation(location);
        return new UnitResponse(unitRepository.save(unit));
    }


    //TODO When tasks and cleaning plan are connected to unit, dont allow deletion
    public void deleteUnitById(int id) {
        Unit unit = unitRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No unit with this id found"));
        unitRepository.delete(unit);
    }
}