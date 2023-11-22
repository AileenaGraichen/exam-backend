package dat3.partner.service;

import dat3.partner.repository.UnitRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
    UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository)
    {
        this.unitRepository = unitRepository;
    }




}
