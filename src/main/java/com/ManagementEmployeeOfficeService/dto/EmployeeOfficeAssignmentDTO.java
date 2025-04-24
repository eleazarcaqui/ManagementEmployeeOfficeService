package com.ManagementEmployeeOfficeService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class EmployeeOfficeAssignmentDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeOfficeAssignmentRequest {
        private Long employeeId;
        private Long officeId;
        private Boolean active;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeOfficeAssignmentResponse {
        private Long id;
        private Long employeeId;
        private Long officeId;
        private LocalDateTime assignmentDate;
        private Boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
