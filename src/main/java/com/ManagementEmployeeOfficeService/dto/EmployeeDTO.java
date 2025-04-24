package com.ManagementEmployeeOfficeService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeRequest {
        private String name;
        private String dni;
        private String phone;
        private String address;
        private LocalDate birthDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeResponse {
        private Long id;
        private String name;
        private String dni;
        private String phone;
        private String address;
        private LocalDate birthDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}