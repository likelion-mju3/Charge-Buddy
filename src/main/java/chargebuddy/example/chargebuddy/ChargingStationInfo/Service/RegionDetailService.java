package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.RegionDetail;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.RegionDetailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionDetailService {
    private final RegionDetailRepository regionDetailRepository;

    //
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isRegionDetailExist(String zscode){
        RegionDetail regionDetail = entityManager.find(RegionDetail.class, zscode);
        return regionDetail != null;
    }

    public List<String> getZscodeListByZcode(String zcode){
        List<RegionDetail> regionDetails = regionDetailRepository.findByZcodeZcode(zcode);
        return regionDetails.stream().map(RegionDetail::getRegionDetailName).toList();
    }
}
