package com.ManagementEmployeeOfficeService.controller;

import com.ManagementEmployeeOfficeService.dto.OfficeDTO;
import com.ManagementEmployeeOfficeService.dto.OfficeWithEmployeeDTO;
import com.ManagementEmployeeOfficeService.service.OfficeService;
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
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping
    public ResponseEntity<Page<OfficeDTO.OfficeResponse>> getAllOffices(Pageable pageable) {
        return ResponseEntity.ok(officeService.getAllOffices(pageable));
    }

    @GetMapping("/with-employee")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<List<OfficeWithEmployeeDTO>> getAllOfficesWithEmployeeCount() {
        return ResponseEntity.ok(officeService.getAllOfficesWithEmployeeCount());
    }

    @GetMapping("/active")
    public ResponseEntity<List<OfficeDTO.OfficeResponse>> getActiveOffices() {
        return ResponseEntity.ok(officeService.getActiveOffices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDTO.OfficeResponse> getOfficeById(@PathVariable Long id) {
        return ResponseEntity.ok(officeService.getOfficeById(id));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<OfficeDTO.OfficeResponse>> getOfficesByCity(@PathVariable String city) {
        return ResponseEntity.ok(officeService.getOfficesByCity(city));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<OfficeDTO.OfficeResponse>> getOfficesByCountry(@PathVariable String country) {
        return ResponseEntity.ok(officeService.getOfficesByCountry(country));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<OfficeDTO.OfficeResponse> createOffice(@Valid @RequestBody OfficeDTO.OfficeRequest request) {
        return new ResponseEntity<>(officeService.createOffice(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<OfficeDTO.OfficeResponse> updateOffice(
            @PathVariable Long id,
            @Valid @RequestBody OfficeDTO.OfficeRequest request) {
        return ResponseEntity.ok(officeService.updateOffice(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('user')")
    public ResponseEntity<OfficeDTO.OfficeResponse> deactivateOffice(@PathVariable Long id) {
        return ResponseEntity.ok(officeService.deactivateOffice(id));
    }
}