package com.ManagementEmployeeOfficeService.controller;

import com.ManagementEmployeeOfficeService.dto.EmployeeDTO;
import com.ManagementEmployeeOfficeService.dto.EmployeeWithOfficesDTO;
import com.ManagementEmployeeOfficeService.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Page<EmployeeDTO.EmployeeResponse>> getAllEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }
    @GetMapping("/with-offices")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Page<EmployeeWithOfficesDTO>> getAllEmployeesWithOffices(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployeesWithOffices(pageable));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/dni/{dni}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> getEmployeeByDni(@PathVariable String dni) {
        return ResponseEntity.ok(employeeService.getEmployeeByDni(dni));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeDTO.EmployeeRequest request) {
        return new ResponseEntity<>(employeeService.createEmployee(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeDTO.EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO.EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}