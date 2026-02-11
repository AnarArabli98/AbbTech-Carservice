package com.abbtech.dto;

import com.abbtech.annotation.LogIgnore;
import com.abbtech.exception.CarException;

public record CarDetailsDto(
        Integer id,
        String engineNumber,
        String registrationCode,
        String color,
        String insuranceNumber,
        String fuelType,
        String engineCapacity
) {
}

