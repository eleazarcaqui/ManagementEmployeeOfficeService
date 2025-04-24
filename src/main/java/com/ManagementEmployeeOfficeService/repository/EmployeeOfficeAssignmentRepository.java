package com.ManagementEmployeeOfficeService.repository;

import com.ManagementEmployeeOfficeService.entity.EmployeeOfficeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeOfficeAssignmentRepository extends JpaRepository<EmployeeOfficeAssignment, Long> {

    List<EmployeeOfficeAssignment> findByEmployeeId(Long employeeId);

    List<EmployeeOfficeAssignment> findByOfficeId(Long officeId);

    List<EmployeeOfficeAssignment> findByEmployeeIdAndActive(Long employeeId, Boolean active);

    List<EmployeeOfficeAssignment> findByOfficeIdAndActive(Long officeId, Boolean active);

    Optional<EmployeeOfficeAssignment> findByEmployeeIdAndOfficeId(Long employeeId, Long officeId);

    @Query("SELECT a FROM EmployeeOfficeAssignment a WHERE a.employeeId = :employeeId AND a.officeId = :officeId AND a.active = true")
    Optional<EmployeeOfficeAssignment> findActiveAssignment(@Param("employeeId") Long employeeId, @Param("officeId") Long officeId);

    boolean existsByEmployeeIdAndOfficeIdAndActive(Long employeeId, Long officeId, Boolean active);
    long countByOfficeIdAndActive(Long officeId, Boolean active);
}