package com.ManagementEmployeeOfficeService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_office_assignments", uniqueConstraints = {
        @UniqueConstraint(name = "unique_assignment", columnNames = {"employee_id", "office_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOfficeAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "office_id", nullable = false)
    private Long officeId;

    @Column(name = "assignment_date")
    private LocalDateTime assignmentDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.assignmentDate = LocalDateTime.now();
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}