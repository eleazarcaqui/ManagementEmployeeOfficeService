package com.ManagementEmployeeOfficeService.controller;

import com.ManagementEmployeeOfficeService.dto.EmployeeOfficeAssignmentDTO;
import com.ManagementEmployeeOfficeService.service.EmployeeOfficeAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-office-assignments")
@RequiredArgsConstructor
public class EmployeeOfficeAssignmentController {

    private final EmployeeOfficeAssignmentService assignmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Page<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse>> getAllAssignments(Pageable pageable) {
        return ResponseEntity.ok(assignmentService.getAllAssignments(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getAssignmentById(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse>> getAssignmentsByEmployeeId(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByEmployeeId(employeeId));
    }

    @GetMapping("/office/{officeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse>> getAssignmentsByOfficeId(
            @PathVariable Long officeId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByOfficeId(officeId));
    }

    @GetMapping("/employee/{employeeId}/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse>> getActiveAssignmentsByEmployeeId(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(assignmentService.getActiveAssignmentsByEmployeeId(employeeId));
    }

    @GetMapping("/office/{officeId}/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse>> getActiveAssignmentsByOfficeId(
            @PathVariable Long officeId) {
        return ResponseEntity.ok(assignmentService.getActiveAssignmentsByOfficeId(officeId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> createAssignment(
            @Valid @RequestBody EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {
        return new ResponseEntity<>(assignmentService.createAssignment(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.updateAssignment(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Void> deactivateAssignment(@PathVariable Long id) {
        assignmentService.deactivateAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}