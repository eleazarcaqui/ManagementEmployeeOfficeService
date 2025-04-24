package com.ManagementEmployeeOfficeService.service;

import com.ManagementEmployeeOfficeService.dto.EmployeeOfficeAssignmentDTO;
import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import com.ManagementEmployeeOfficeService.mapper.EmployeeOfficeAssignmentMapper;
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
public class EmployeeOfficeAssignmentService {

    private final EmployeeOfficeAssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final OfficeRepository officeRepository;
    private final EmployeeOfficeAssignmentMapper mapper;

    @Transactional(readOnly = true)
    public Page<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getAllAssignments(Pageable pageable) {
        return assignmentRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse getAssignmentById(Long id) {
        EmployeeOfficeAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found with ID: " + id));
        return mapper.toDto(assignment);
    }

    @Transactional(readOnly = true)
    public List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getAssignmentsByEmployeeId(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getAssignmentsByOfficeId(Long officeId) {
        return assignmentRepository.findByOfficeId(officeId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getActiveAssignmentsByEmployeeId(Long employeeId) {
        return assignmentRepository.findByEmployeeIdAndActive(employeeId, true).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> getActiveAssignmentsByOfficeId(Long officeId) {
        return assignmentRepository.findByOfficeIdAndActive(officeId, true).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse createAssignment(
            EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {

        if (!employeeRepository.existsById(request.getEmployeeId())) {
            throw new NoSuchElementException("Employee not found with ID: " + request.getEmployeeId());
        }

        if (!officeRepository.existsById(request.getOfficeId())) {
            throw new NoSuchElementException("Office not found with ID: " + request.getOfficeId());
        }

        if (assignmentRepository.existsByEmployeeIdAndOfficeIdAndActive(
                request.getEmployeeId(), request.getOfficeId(), true)) {
            throw new IllegalStateException("Active assignment already exists for this employee and office");
        }

        EmployeeOfficeAssignment assignment = mapper.toEntity(request);
        EmployeeOfficeAssignment savedAssignment = assignmentRepository.save(assignment);

        return mapper.toDto(savedAssignment);
    }

    @Transactional
    public EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse updateAssignment(
            Long id, EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest request) {

        EmployeeOfficeAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found with ID: " + id));
        if (request.getEmployeeId() != null && !request.getEmployeeId().equals(assignment.getEmployeeId())) {
            if (!employeeRepository.existsById(request.getEmployeeId())) {
                throw new NoSuchElementException("Employee not found with ID: " + request.getEmployeeId());
            }
        }

        if (request.getOfficeId() != null && !request.getOfficeId().equals(assignment.getOfficeId())) {
            if (!officeRepository.existsById(request.getOfficeId())) {
                throw new NoSuchElementException("Office not found with ID: " + request.getOfficeId());
            }
        }

        if ((request.getEmployeeId() != null && !request.getEmployeeId().equals(assignment.getEmployeeId())) ||
                (request.getOfficeId() != null && !request.getOfficeId().equals(assignment.getOfficeId()))) {

            Long newEmployeeId = request.getEmployeeId() != null ? request.getEmployeeId() : assignment.getEmployeeId();
            Long newOfficeId = request.getOfficeId() != null ? request.getOfficeId() : assignment.getOfficeId();

            assignmentRepository.findByEmployeeIdAndOfficeId(newEmployeeId, newOfficeId)
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new IllegalStateException(
                                    "Assignment already exists for this employee and office");
                        }
                    });
        }

        mapper.updateEntityFromDto(assignment, request);
        EmployeeOfficeAssignment updatedAssignment = assignmentRepository.save(assignment);
        return mapper.toDto(updatedAssignment);
    }

    @Transactional
    public void deactivateAssignment(Long id) {
        EmployeeOfficeAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment not found with ID: " + id));

        assignment.setActive(false);
        assignmentRepository.save(assignment);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new NoSuchElementException("Assignment not found with ID: " + id);
        }

        assignmentRepository.deleteById(id);
    }
}