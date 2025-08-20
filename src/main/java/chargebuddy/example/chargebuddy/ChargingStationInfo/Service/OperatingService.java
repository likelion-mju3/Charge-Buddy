package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Operating;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.OperatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatingService {
    private final OperatingRepository operatingRepository;

    //Operating 조회용
    public Optional<Operating> findByBnmOptional(String bnm){
        return operatingRepository.findByBnm(bnm);
    }

}
