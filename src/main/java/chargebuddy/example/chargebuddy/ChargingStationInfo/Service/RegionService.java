package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Region;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {
    private final RegionRepository regionRepository;

    public List<String> getZcodeList(){
        List<Region> regions = regionRepository.findAll();
        return regions.stream().map(Region::getZcode).toList();
    }

    public List<String> getRegionNameList(){
        List<Region> regions = regionRepository.findAll();
        return regions.stream().map(Region::getRegionName).toList();
    }
}
