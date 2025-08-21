package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Stat {
    TYPE_0("0", "알수없음"),
    TYPE_1("1", "통신이상"),
    TYPE_2("2", "충전대기"),
    TYPE_3("3", "충전중"),
    TYPE_4("4", "운영중지"),
    TYPE_5("5", "점검중"),
    TYPE_9("9", "상태미확인");

    private final String number;
    private final String value;
}
