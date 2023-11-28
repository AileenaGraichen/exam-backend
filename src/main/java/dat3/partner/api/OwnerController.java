package dat3.partner.api;

import dat3.partner.dto.OwnerRequest;
import dat3.partner.dto.OwnerResponse;
import dat3.partner.service.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {
    OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public Page<OwnerResponse> getAllOwners(Pageable pageable){
        return ownerService.getAllOwners(pageable);
    }

    @GetMapping("/{id}")
    public OwnerResponse getOwnerById(@PathVariable int id){
        return ownerService.getOwnerById(id);
    }

    @GetMapping("search/{search}")
    public List<OwnerResponse> getOwnersBySearch(@PathVariable(required = false) String search){
        List<OwnerResponse> ownerResponses = ownerService.getOwnersBySearch(search);
        return ownerResponses;
    }

    @GetMapping("/byMobile")
    public OwnerResponse getGetOwnerByMobile(Pageable pageable, @RequestParam(name="mobile", required = false) String mobile){
        return ownerService.getOwnerByMobile(pageable, mobile);
    }

    @PostMapping
    public OwnerResponse addOwner(@RequestBody OwnerRequest body){
        return ownerService.addOwner(body);
    }

    @PatchMapping("/{id}")
    public OwnerResponse editOwner(@PathVariable int id, @RequestBody OwnerRequest body){
        return ownerService.editOwner(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable int id){
        ownerService.deleteOwner(id);
    }
}
