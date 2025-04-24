package com.ManagementEmployeeOfficeService.mapper;

import com.ManagementEmployeeOfficeService.dto.EmployeeOfficeAssignmentDTO;
import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import org.springframework.stereotype.Component;

@Component
public class EmployeeOfficeAssignmentMapper {

    public EmployeeOfficeAssignment toEntity(EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {
        EmployeeOfficeAssignment assignment = new EmployeeOfficeAssignment();
        assignment.setEmployeeId(request.getEmployeeId());
        assignment.setOfficeId(request.getOfficeId());
        assignment.setActive(request.getActive() != null ? request.getActive() : true);
        return assignment;
    }

    public void updateEntityFromDto(EmployeeOfficeAssignment assignment, EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {
        if (request.getEmployeeId() != null) assignment.setEmployeeId(request.getEmployeeId());
        if (request.getOfficeId() != null) assignment.setOfficeId(request.getOfficeId());
        if (request.getActive() != null) assignment.setActive(request.getActive());
    }

    public EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse toDto(EmployeeOfficeAssignment assignment) {
        return new EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse(
                assignment.getId(),
                assignment.getEmployeeId(),
                assignment.getOfficeId(),
                assignment.getAssignmentDate(),
                assignment.getActive(),
                assignment.getCreatedAt(),
                assignment.getUpdatedAt()
        );
    }
}