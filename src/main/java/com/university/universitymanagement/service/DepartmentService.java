package com.university.universitymanagement.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.repository.*;
import java.util.List;
import static com.university.universitymanagement.utility.PaginationUtils.paginate;

/**
 * Service class for managing Department entities.
 * Handles CRUD and audit logging.
 */
@Service
public class DepartmentService {
    private final DepartmentRepository repo;
    private final AuditLogService auditLogService;
    private final FacultyRepository facultyRepository;

    public DepartmentService(DepartmentRepository repo, AuditLogService auditLogService,
                             FacultyRepository facultyRepository) {
        this.repo = repo;
        this.auditLogService = auditLogService;
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public Department create(Department department, User user) {
        if (department.getFaculty() == null || department.getFaculty().getId() == null)
            throw new RuntimeException("Faculty must be provided");

        Faculty faculty = facultyRepository.findById(department.getFaculty().getId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        department.setFaculty(faculty);

        Department saved = repo.save(department);
        auditLogService.log("departments", saved.getId(), user, "CREATE", null, saved.toString());
        return saved;
    }

    public List<Department> all(int page, int size) {
        List<Department> departments = repo.findByIsActiveTrueAndIsDeletedFalse();
        return paginate(departments, page, size);
    }

    public Department get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @Transactional
    public Department update(Long id, Department data, User user) {
        Department department = get(id);
        String old = department.toString();
        department.setName(data.getName());
        department.setLocation(data.getLocation());
        department.setIsActive(data.getIsActive());
        department.setIsDeleted(data.getIsDeleted());

        if (data.getFaculty() != null && data.getFaculty().getId() != null) {
            Faculty faculty = facultyRepository.findById(data.getFaculty().getId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));
            department.setFaculty(faculty);
        }

        Department updated = repo.save(department);
        auditLogService.log("departments", id, user, "UPDATE", old, updated.toString());
        return updated;
    }

    @Transactional
    public void delete(Long id, User user) {
        Department department = get(id);
        department.setIsDeleted(true);
        repo.save(department);
        auditLogService.log("departments", id, user, "DELETE", department.toString(), null);
    }
}
