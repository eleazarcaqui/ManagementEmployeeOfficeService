package com.ManagementEmployeeOfficeService.repository;

import com.ManagementEmployeeOfficeService.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findByActiveTrue();
    List<Office> findByCity(String city);
    List<Office> findByCountry(String country);
    Optional<Office> findByNameAndAddressAndCity(String name, String address, String city);


}