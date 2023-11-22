package dat3.partner.service;

import dat3.partner.repository.UnitInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitInfoService {
    UnitInfoRepository unitInfoRepository;

    public UnitInfoService(UnitInfoRepository unitInfoRepository)
    {
        this.unitInfoRepository = unitInfoRepository;
    }


}
