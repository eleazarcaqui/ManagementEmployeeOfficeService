package com.ManagementEmployeeOfficeService.mapper;

import com.ManagementEmployeeOfficeService.dto.OfficeDTO;
import com.ManagementEmployeeOfficeService.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {

    public Office toEntity(OfficeDTO.OfficeRequest request) {
        Office office = new Office();
        office.setName(request.getName());
        office.setAddress(request.getAddress());
        office.setCity(request.getCity());
        office.setCountry(request.getCountry());
        office.setPostalCode(request.getPostalCode());
        office.setPhone(request.getPhone());

        if (request.getActive() != null) {
            office.setActive(request.getActive());
        }

        return office;
    }

    public void updateEntityFromDto(Office office, OfficeDTO.OfficeRequest request) {
        if (request.getName() != null) office.setName(request.getName());
        if (request.getAddress() != null) office.setAddress(request.getAddress());
        if (request.getCity() != null) office.setCity(request.getCity());
        if (request.getCountry() != null) office.setCountry(request.getCountry());
        if (request.getPostalCode() != null) office.setPostalCode(request.getPostalCode());
        if (request.getPhone() != null) office.setPhone(request.getPhone());
        if (request.getActive() != null) office.setActive(request.getActive());
    }

    public OfficeDTO.OfficeResponse toDto(Office office) {
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

}