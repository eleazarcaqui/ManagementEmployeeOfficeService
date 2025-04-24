package com.ManagementEmployeeOfficeService.service;

import com.ManagementEmployeeOfficeService.dto.EmployeeDTO;
import com.ManagementEmployeeOfficeService.dto.OfficeDTO;
import com.ManagementEmployeeOfficeService.dto.OfficeWithEmployeeDTO;
import com.ManagementEmployeeOfficeService.entity.Employee;
import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import com.ManagementEmployeeOfficeService.entity.Office;
import com.ManagementEmployeeOfficeService.mapper.OfficeMapper;
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
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;
    private final EmployeeOfficeAssignmentRepository employeeOfficeAssignmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<OfficeDTO.OfficeResponse> getAllOffices(Pageable pageable) {
        return officeRepository.findAll(pageable)
                .map(officeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<OfficeWithEmployeeDTO> getAllOfficesWithEmployeeCount() {
        List<Office> offices = officeRepository.findByActiveTrue();

        return offices.stream().map(office -> {
            OfficeWithEmployeeDTO dto = new OfficeWithEmployeeDTO();
            dto.setId(office.getId());
            dto.setName(office.getName());
            dto.setAddress(office.getAddress());
            dto.setCity(office.getCity());
            dto.setCountry(office.getCountry());
            dto.setPostalCode(office.getPostalCode());
            dto.setPhone(office.getPhone());
            dto.setActive(office.getActive());
            dto.setCreatedAt(office.getCreatedAt());
            dto.setUpdatedAt(office.getUpdatedAt());

            List<EmployeeOfficeAssignment> activeAssignments =
                    employeeOfficeAssignmentRepository.findByOfficeIdAndActive(office.getId(), true);

            List<EmployeeDTO.EmployeeResponse> employees = activeAssignments.stream()
                    .map(assignment -> {
                        Employee employee = employeeRepository.findById(assignment.getEmployeeId())
                                .orElse(null);
                        if (employee != null) {
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
                        return null;
                    })
                    .filter(employee -> employee != null)
                    .collect(Collectors.toList());

            dto.setEmployees(employees);
            return dto;
        }).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<OfficeDTO.OfficeResponse> getActiveOffices() {
        return officeRepository.findByActiveTrue().stream()
                .map(officeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OfficeDTO.OfficeResponse getOfficeById(Long id) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Office not found with ID: " + id));
        return officeMapper.toDto(office);
    }

    @Transactional(readOnly = true)
    public List<OfficeDTO.OfficeResponse> getOfficesByCity(String city) {
        return officeRepository.findByCity(city).stream()
                .map(officeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OfficeDTO.OfficeResponse> getOfficesByCountry(String country) {
        return officeRepository.findByCountry(country).stream()
                .map(officeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OfficeDTO.OfficeResponse createOffice(OfficeDTO.OfficeRequest request) {
        // Check if office with same name, address and city already exists
        officeRepository.findByNameAndAddressAndCity(
                        request.getName(), request.getAddress(), request.getCity())
                .ifPresent(office -> {
                    throw new IllegalStateException("Office already exists with the same name, address and city");
                });

        Office office = officeMapper.toEntity(request);
        Office savedOffice = officeRepository.save(office);
        return officeMapper.toDto(savedOffice);
    }

    @Transactional
    public OfficeDTO.OfficeResponse updateOffice(Long id, OfficeDTO.OfficeRequest request) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Office not found with ID: " + id));

        officeMapper.updateEntityFromDto(office, request);
        Office updatedOffice = officeRepository.save(office);
        return officeMapper.toDto(updatedOffice);
    }

    @Transactional
    public void deleteOffice(Long id) {
        if (!officeRepository.existsById(id)) {
            throw new NoSuchElementException("Office not found with ID: " + id);
        }
        officeRepository.deleteById(id);
    }

    @Transactional
    public OfficeDTO.OfficeResponse deactivateOffice(Long id) {
        Office office = officeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Office not found with ID: " + id));

        office.setActive(false);
        Office updatedOffice = officeRepository.save(office);
        return officeMapper.toDto(updatedOffice);
    }
}