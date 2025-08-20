package chargebuddy.example.chargebuddy.ChargingStationInfo.batch;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Operating;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.RegionDetail;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.OperatingRepository;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.RegionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class ChargerApiBatchProcessor implements ItemProcessor<List<ChargerApiForm>, List<ChargerApiForm>> {
    private final RegionDetailRepository regionDetailRepository;
    private final OperatingRepository operatingRepository;

    @Override
    public List<ChargerApiForm> process(List<ChargerApiForm> item) throws Exception {
        List<RegionDetail> regionList = regionDetailRepository.findAll();
        List<Operating> operatingsList = operatingRepository.findAll();
        HashMap<String, String> newCompanyMap = new HashMap<>();

        //조건 필터
        List<ChargerApiForm> filterList = item.stream()
                .filter(i -> isValidItem(i, regionList, operatingsList, newCompanyMap))
                .toList();

        //감지된 새로운 기관 저장
        List<Operating> newOperatingList = newCompanyMap.entrySet().stream()
                .map(
                        entry -> Operating.builder()
                                .busiId(entry.getKey())
                                .bnm(entry.getValue())
                                .build()
                ).toList();

        if(!newOperatingList.isEmpty()){
            log.info("[Batch] : 신규기관 {} 개 저장 완료", newOperatingList.size());
            operatingRepository.saveAll(newOperatingList);
        }
        return filterList;
    }

    private boolean isValidItem(
            ChargerApiForm item, List<RegionDetail> regionDetails,
            List<Operating> operatingList, Map<String, String> newCompanyMap
    ){
        if (item.getStatId() != null && !item.getStatId().isEmpty()
                && item.getStatNm() != null && !item.getStatNm().isEmpty()
                && item.getLat() != null && item.getLng() != null
                && item.getAddr() != null && !item.getAddr().isEmpty()
                && item.getChgerId() != null && !item.getChgerId().isEmpty()
                && item.getChgerType() != null && !item.getChgerType().isEmpty()
                && item.getBusiId() != null && item.getBnm() != null
                && item.getZscode() != null && regionDetails.stream().anyMatch(
                region -> region.getZscode().contentEquals(item.getZscode()))
        ) {
            if (operatingList.stream().noneMatch(company -> company.getBusiId().contentEquals(item.getBusiId()))) {
                newCompanyMap.put(item.getBusiId(), item.getBnm());
            }
            return true;
        }
        return false;
    }
}
