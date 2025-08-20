package com.chargebuddy.api.dto.map;

import chargebuddy.example.chargebuddy.Dto.map.PlaceMarker;
import chargebuddy.example.chargebuddy.Dto.map.StationMarker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "지도 내 충전소/상권 결과 응답")
public class MapPlaceResponse {

    @Schema(description = "충전소 마커 목록")
    @NotNull
    private List<StationMarker> stations;

    @Schema(description = "상권 마커 목록 (탭 상태에 따라 프론트가 렌더 제어)")
    @NotNull
    private List<PlaceMarker> places;
}