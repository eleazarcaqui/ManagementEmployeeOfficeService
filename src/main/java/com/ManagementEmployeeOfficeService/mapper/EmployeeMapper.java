package com.ManagementEmployeeOfficeService.mapper;

import com.ManagementEmployeeOfficeService.dto.EmployeeDTO;
import com.ManagementEmployeeOfficeService.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeDTO.EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setDni(request.getDni());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setBirthDate(request.getBirthDate());
        return employee;
    }

    public void updateEntityFromDto(Employee employee, EmployeeDTO.EmployeeRequest request) {
        if (request.getName() != null) employee.setName(request.getName());
        if (request.getDni() != null) employee.setDni(request.getDni());
        if (request.getPhone() != null) employee.setPhone(request.getPhone());
        if (request.getAddress() != null) employee.setAddress(request.getAddress());
        if (request.getBirthDate() != null) employee.setBirthDate(request.getBirthDate());
    }

    public EmployeeDTO.EmployeeResponse toDto(Employee employee) {
        return new EmployeeDTO.EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getDni(),
                employee.getPhone(),
                employee.getAddress(),
                employee.getBirthDate(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}