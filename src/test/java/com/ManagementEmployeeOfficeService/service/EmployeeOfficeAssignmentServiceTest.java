package com.ManagementEmployeeOfficeService.service;

import com.ManagementEmployeeOfficeService.dto.EmployeeOfficeAssignmentDTO;
import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import com.ManagementEmployeeOfficeService.mapper.EmployeeOfficeAssignmentMapper;
import com.ManagementEmployeeOfficeService.repository.EmployeeOfficeAssignmentRepository;
import com.ManagementEmployeeOfficeService.repository.EmployeeRepository;
import com.ManagementEmployeeOfficeService.repository.OfficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeOfficeAssignmentServiceTest {

    @Mock
    private EmployeeOfficeAssignmentRepository assignmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private EmployeeOfficeAssignmentMapper mapper;

    @InjectMocks
    private EmployeeOfficeAssignmentService service;

    private EmployeeOfficeAssignment assignment;
    private EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse responseDTO;
    private EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest requestDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        assignment = new EmployeeOfficeAssignment();
        assignment.setId(1L);
        assignment.setEmployeeId(10L);
        assignment.setOfficeId(20L);
        assignment.setAssignmentDate(now);
        assignment.setActive(true);
        assignment.setCreatedAt(now);
        assignment.setUpdatedAt(now);

        responseDTO = new EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse();
        responseDTO.setId(1L);
        responseDTO.setEmployeeId(10L);
        responseDTO.setOfficeId(20L);
        responseDTO.setAssignmentDate(now);
        responseDTO.setActive(true);

        requestDTO = new EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentRequest();
        requestDTO.setEmployeeId(10L);
        requestDTO.setOfficeId(20L);
    }

    @Test
    @DisplayName("Should return all assignments")
    void getAllAssignmentsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<EmployeeOfficeAssignment> assignments = Arrays.asList(assignment);
        Page<EmployeeOfficeAssignment> assignmentPage = new PageImpl<>(assignments, pageable, assignments.size());

        when(assignmentRepository.findAll(pageable)).thenReturn(assignmentPage);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        Page<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> result = service.getAllAssignments(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(responseDTO, result.getContent().get(0));
        verify(assignmentRepository).findAll(pageable);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should return assignment by ID")
    void getAssignmentByIdTest() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse result = service.getAssignmentById(1L);

        assertEquals(responseDTO, result);
        verify(assignmentRepository).findById(1L);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should throw exception when assignment not found by ID")
    void getAssignmentByIdNotFoundTest() {
        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getAssignmentById(999L));
        verify(assignmentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should return assignments by employee ID")
    void getAssignmentsByEmployeeIdTest() {
        List<EmployeeOfficeAssignment> assignments = Arrays.asList(assignment);

        when(assignmentRepository.findByEmployeeId(10L)).thenReturn(assignments);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> result = service.getAssignmentsByEmployeeId(10L);

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(assignmentRepository).findByEmployeeId(10L);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should return assignments by office ID")
    void getAssignmentsByOfficeIdTest() {
        List<EmployeeOfficeAssignment> assignments = Arrays.asList(assignment);

        when(assignmentRepository.findByOfficeId(20L)).thenReturn(assignments);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> result = service.getAssignmentsByOfficeId(20L);

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(assignmentRepository).findByOfficeId(20L);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should return active assignments by employee ID")
    void getActiveAssignmentsByEmployeeIdTest() {
        List<EmployeeOfficeAssignment> assignments = Arrays.asList(assignment);

        when(assignmentRepository.findByEmployeeIdAndActive(10L, true)).thenReturn(assignments);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> result = service.getActiveAssignmentsByEmployeeId(10L);

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(assignmentRepository).findByEmployeeIdAndActive(10L, true);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should return active assignments by office ID")
    void getActiveAssignmentsByOfficeIdTest() {
        List<EmployeeOfficeAssignment> assignments = Arrays.asList(assignment);

        when(assignmentRepository.findByOfficeIdAndActive(20L, true)).thenReturn(assignments);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        List<EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse> result = service.getActiveAssignmentsByOfficeId(20L);

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(assignmentRepository).findByOfficeIdAndActive(20L, true);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should create assignment successfully")
    void createAssignmentTest() {
        when(employeeRepository.existsById(10L)).thenReturn(true);
        when(officeRepository.existsById(20L)).thenReturn(true);
        when(assignmentRepository.existsByEmployeeIdAndOfficeIdAndActive(10L, 20L, true)).thenReturn(false);
        when(mapper.toEntity(requestDTO)).thenReturn(assignment);
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse result = service.createAssignment(requestDTO);

        assertEquals(responseDTO, result);
        verify(employeeRepository).existsById(10L);
        verify(officeRepository).existsById(20L);
        verify(assignmentRepository).existsByEmployeeIdAndOfficeIdAndActive(10L, 20L, true);
        verify(mapper).toEntity(requestDTO);
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should throw exception when employee not found during creation")
    void createAssignmentEmployeeNotFoundTest() {
        when(employeeRepository.existsById(10L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> service.createAssignment(requestDTO));
        verify(employeeRepository).existsById(10L);
        verifyNoInteractions(mapper, assignmentRepository);
    }

    @Test
    @DisplayName("Should throw exception when office not found during creation")
    void createAssignmentOfficeNotFoundTest() {
        when(employeeRepository.existsById(10L)).thenReturn(true);
        when(officeRepository.existsById(20L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> service.createAssignment(requestDTO));
        verify(employeeRepository).existsById(10L);
        verify(officeRepository).existsById(20L);
        verifyNoInteractions(mapper, assignmentRepository);
    }

    @Test
    @DisplayName("Should throw exception when active assignment already exists")
    void createAssignmentActiveAssignmentExistsTest() {
        when(employeeRepository.existsById(10L)).thenReturn(true);
        when(officeRepository.existsById(20L)).thenReturn(true);
        when(assignmentRepository.existsByEmployeeIdAndOfficeIdAndActive(10L, 20L, true)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> service.createAssignment(requestDTO));
        verify(employeeRepository).existsById(10L);
        verify(officeRepository).existsById(20L);
        verify(assignmentRepository).existsByEmployeeIdAndOfficeIdAndActive(10L, 20L, true);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Should update assignment successfully")
    void updateAssignmentTest() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(mapper.toDto(assignment)).thenReturn(responseDTO);

        EmployeeOfficeAssignmentDTO.EmployeeOfficeAssignmentResponse result = service.updateAssignment(1L, requestDTO);

        assertEquals(responseDTO, result);
        verify(assignmentRepository).findById(1L);
        verify(mapper).updateEntityFromDto(assignment, requestDTO);
        verify(assignmentRepository).save(assignment);
        verify(mapper).toDto(assignment);
    }

    @Test
    @DisplayName("Should throw exception when assignment not found during update")
    void updateAssignmentNotFoundTest() {
        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateAssignment(999L, requestDTO));
        verify(assignmentRepository).findById(999L);
        verifyNoMoreInteractions(assignmentRepository, mapper);
    }

    @Test
    @DisplayName("Should deactivate assignment successfully")
    void deactivateAssignmentTest() {
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(assignment));

        service.deactivateAssignment(1L);

        assertFalse(assignment.getActive());
        verify(assignmentRepository).findById(1L);
        verify(assignmentRepository).save(assignment);
    }

    @Test
    @DisplayName("Should throw exception when assignment not found during deactivation")
    void deactivateAssignmentNotFoundTest() {
        when(assignmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.deactivateAssignment(999L));
        verify(assignmentRepository).findById(999L);
        verifyNoMoreInteractions(assignmentRepository);
    }

    @Test
    @DisplayName("Should delete assignment successfully")
    void deleteAssignmentTest() {
        when(assignmentRepository.existsById(1L)).thenReturn(true);

        service.deleteAssignment(1L);

        verify(assignmentRepository).existsById(1L);
        verify(assignmentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when assignment not found during deletion")
    void deleteAssignmentNotFoundTest() {
        when(assignmentRepository.existsById(999L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> service.deleteAssignment(999L));
        verify(assignmentRepository).existsById(999L);
        verifyNoMoreInteractions(assignmentRepository);
    }
}