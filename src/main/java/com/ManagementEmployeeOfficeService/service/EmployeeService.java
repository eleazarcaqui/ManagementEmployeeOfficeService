package com.ManagementEmployeeOfficeService.service;

import com.ManagementEmployeeOfficeService.dto.EmployeeDTO;
import com.ManagementEmployeeOfficeService.dto.EmployeeWithOfficesDTO;
import com.ManagementEmployeeOfficeService.dto.OfficeDTO;
import com.ManagementEmployeeOfficeService.entity.Employee;
import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import com.ManagementEmployeeOfficeService.entity.Office;
import com.ManagementEmployeeOfficeService.mapper.EmployeeMapper;
import com.ManagementEmployeeOfficeService.repository.EmployeeOfficeAssignmentRepository;
import com.ManagementEmployeeOfficeService.repository.EmployeeRepository;
import com.ManagementEmployeeOfficeService.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeOfficeAssignmentRepository employeeOfficeAssignmentRepository;
    private final OfficeRepository officeRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeDTO.EmployeeResponse> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO.EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO.EmployeeResponse getEmployeeByDni(String dni) {
        Employee employee = employeeRepository.findByDni(dni)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with DNI: " + dni));
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeWithOfficesDTO> getAllEmployeesWithOffices(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.map(employee -> {
            EmployeeWithOfficesDTO dto = new EmployeeWithOfficesDTO();
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setDni(employee.getDni());
            dto.setPhone(employee.getPhone());
            dto.setAddress(employee.getAddress());
            dto.setBirthDate(employee.getBirthDate());
            dto.setCreatedAt(employee.getCreatedAt());
            dto.setUpdatedAt(employee.getUpdatedAt());

            List<EmployeeOfficeAssignment> activeAssignments =
                    employeeOfficeAssignmentRepository.findByEmployeeIdAndActive(employee.getId(), true);

            List<OfficeDTO.OfficeResponse> offices = activeAssignments.stream()
                    .map(assignment -> {
                        Office office = officeRepository.findById(assignment.getOfficeId())
                                .orElse(null);
                        if (office != null) {
                            return new OfficeDTO.OfficeResponse(
                                    office.getId(),
                                    office.getName(),
                                    office.getAddress(),
                                    office.getCity(),
                                    office.getCountry(),
                                    office.getPostalCode(),
                                    office.getPhone(),
                                    office.getActive(),
                                    office.getCreatedAt(),
                                    office.getUpdatedAt()
                            );
                        }
                        return null;
                    })
                    .filter(office -> office != null)
                    .collect(Collectors.toList());

            dto.setOffices(offices);
            return dto;
        });
    }

    @Transactional
    public EmployeeDTO.EmployeeResponse createEmployee(EmployeeDTO.EmployeeRequest request) {
        if (employeeRepository.existsByDni(request.getDni())) {
            throw new IllegalStateException("Employee already exists with DNI: " + request.getDni());
        }

        Employee employee = employeeMapper.toEntity(request);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Transactional
    public EmployeeDTO.EmployeeResponse updateEmployee(Long id, EmployeeDTO.EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + id));

        if (request.getDni() != null && !request.getDni().equals(employee.getDni()) &&
                employeeRepository.existsByDni(request.getDni())) {
            throw new IllegalStateException("Another employee already exists with DNI: " + request.getDni());
        }

        employeeMapper.updateEntityFromDto(employee, request);
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Employee not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }
}