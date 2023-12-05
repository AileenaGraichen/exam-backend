package dat3.partner.api;

import dat3.partner.dto.UnitRequest;
import dat3.partner.dto.UnitResponse;
import dat3.partner.entity.UnitStatus;
import dat3.partner.service.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitController {
    UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public Page<UnitResponse> getAllUnits(Pageable pageable){
        return unitService.getAllUnits(pageable);
    }

    @GetMapping("/{id}")
    public Page<UnitResponse> getUnitsByLocation(Pageable pageable, @PathVariable int id){
        return unitService.getUnitsByLocationId(pageable, id);
    }

    @GetMapping("/oneunit/{id}")
    public UnitResponse getUnitById(@PathVariable int id){
        return unitService.getUnitById(id);
    }

    @GetMapping("owner/{id}")
    public Page<UnitResponse> getUnitsByOwnerId(@PathVariable int id, Pageable pageable){
        return unitService.getUnitsByOwnerId(pageable, id);
    }

    @GetMapping("/by/type")
    public Page<UnitResponse> getUnitsByType(Pageable pageable, @RequestParam(name="type", required = false)String type){
        return unitService.getUnitsByType(pageable, type);
    }

    @PostMapping
    public UnitResponse addUnit(@RequestParam("unitNumber") String unitNumber,
                                @RequestParam("unitStatus") String unitStatus,
                                @RequestParam("locationId") int locationId,
                                @RequestParam("type") String type,
                                @RequestParam(value = "keyCode", required = false) String keyCode,
                                @RequestParam("ownerId") int ownerId,
                                @RequestParam(value = "image", required = false)MultipartFile image) throws IOException {
        byte[] imageByte = image != null ? image.getBytes() : null ;
        UnitRequest body = new UnitRequest(unitNumber, UnitStatus.valueOf(unitStatus), locationId, type, keyCode, ownerId, imageByte);
        return unitService.addUnit(body);
    }

    @PatchMapping("/{id}")
    public UnitResponse editUnit(@PathVariable int id, @RequestBody UnitRequest body){
        return unitService.editUnit(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable int id){
        unitService.deleteUnitById(id);
    }
}
