package com.ManagementEmployeeOfficeService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithOfficesDTO {
    private Long id;
    private String name;
    private String dni;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OfficeDTO.OfficeResponse> offices;
}