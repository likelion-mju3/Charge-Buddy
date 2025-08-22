package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChargerType {
    TYPE_01("01", "DC차데모"),
    TYPE_02("02", "AC완속"),
    TYPE_03("03", "DC차데모+AC3상"),
    TYPE_04("04", "DC콤보"),
    TYPE_05("05", "DC차데모+DC콤보"),
    TYPE_06("06", "DC차데모+AC3상+DC콤보"),
    TYPE_07("07", "AC3상"),
    TYPE_08("08", "DC콤보(완속)"),
    TYPE_09("09", "NACS"),
    TYPE_10("10", "DC콤보+NACS");

    private final String number;
    private final String value;
}
