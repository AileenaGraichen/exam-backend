package dat3.partner.service;

import dat3.partner.dto.OwnerRequest;
import dat3.partner.dto.OwnerResponse;
import dat3.partner.entity.Owner;
import dat3.partner.repository.OwnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {
    OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    public Page<OwnerResponse> getAllOwners(Pageable pageable) {
        Page<Owner> owners = ownerRepository.findAll(pageable);
        return owners.map(owner -> new OwnerResponse(owner));
    }

    public OwnerResponse getOwnerByMobile(Pageable pageable, String mobile) {
        Owner owner = ownerRepository.findByMobile(mobile);
        return new OwnerResponse(owner);
    }

    public OwnerResponse getOwnerById(int id){
        Owner owner = ownerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner with that ID does not exist"));
        return new OwnerResponse(owner);
    }

    public List<OwnerResponse> getOwnersBySearch(String search) {
        List<Owner> owners = new ArrayList<>();
        if(search == null && search.isEmpty()){
            owners = ownerRepository.findAll();
        } else if(containsNumbers(search)){
            owners = ownerRepository.findOwnersByUnitNumber(search);
        }else if(owners.isEmpty()){
            owners = ownerRepository.findAllBySearchValueIgnoreCase(search);
        }

        if(owners.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No owner found for search criteria");
        }

        return owners.stream().map(owner -> new OwnerResponse(owner)).toList();
    }

    public OwnerResponse addOwner(OwnerRequest body) {
        if(ownerRepository.existsOwnerByMobile(body.getMobile())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner with that number already exists");
        }
        Owner newOwner = new Owner();
        newOwner.setFirstName(body.getFirstName());
        newOwner.setLastName(body.getLastName());
        newOwner.setMobile(body.getMobile());
        newOwner.setEmail(body.getEmail());

        newOwner = ownerRepository.save(newOwner);
        return new OwnerResponse(newOwner);
    }

    public OwnerResponse editOwner(int id, OwnerRequest body) {
        if(!ownerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner with that ID does not exist");
        }
        Owner editOwner = ownerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner with that ID does not exist"));
        editOwner.setFirstName(body.getFirstName());
        editOwner.setLastName(body.getLastName());
        editOwner.setMobile(body.getMobile());
        editOwner.setEmail(body.getEmail());

        editOwner = ownerRepository.save(editOwner);
        return new OwnerResponse(editOwner);
    }

    public void deleteOwner(int id) {
        if (ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner with that id does not exist");
        }
    }

    private boolean containsNumbers(String input) {
        // Check if the input string contains numbers using a regular expression
        return input.matches(".*\\d+.*");
    }
}