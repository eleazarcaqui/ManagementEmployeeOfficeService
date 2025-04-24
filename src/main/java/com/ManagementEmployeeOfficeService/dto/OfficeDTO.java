package com.ManagementEmployeeOfficeService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class OfficeDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OfficeRequest {
        private String name;
        private String address;
        private String city;
        private String country;
        private String postalCode;
        private String phone;
        private Boolean active;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OfficeResponse {
        private Long id;
        private String name;
        private String address;
        private String city;
        private String country;
        private String postalCode;
        private String phone;
        private Boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}