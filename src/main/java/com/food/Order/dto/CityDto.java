package com.food.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto {
    private String cityId;
    private String cityName;
    private List<PinCode> pinCodes;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PinCode {
        private String pinCodeId;
        private String PinCode;


    }
}
