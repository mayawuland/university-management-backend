package com.university.universitymanagement.service;

import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.university.universitymanagement.utility.PaginationUtils.paginate;

/**
 * Service class for managing Faculty entities.
 * Provides CRUD operations and audit logging.
 */
@Service
public class FacultyService {
    private final FacultyRepository repo;
    private final AuditLogService auditLogService;
    private final UniversityRepository universityRepository;

    public FacultyService(FacultyRepository repo, AuditLogService auditLogService,
                          UniversityRepository universityRepository) {
        this.repo = repo;
        this.auditLogService = auditLogService;
        this.universityRepository = universityRepository;
    }

    @Transactional
    public Faculty create(Faculty faculty, User user) {
        if (faculty.getUniversity() == null || faculty.getUniversity().getId() == null)
            throw new RuntimeException("University must be provided");

        University university = universityRepository.findById(faculty.getUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        faculty.setUniversity(university);
        Faculty saved = repo.save(faculty);
        auditLogService.log("faculties", saved.getId(), user, "CREATE", null, saved.toString());
        return saved;
    }

    public List<Faculty> all(int page, int size) {
        List<Faculty> faculties = repo.findByIsActiveTrueAndIsDeletedFalse();
        return paginate(faculties, page, size);
    }

    public Faculty get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));
    }

    @Transactional
    public Faculty update(Long id, Faculty data, User user) {
        Faculty faculty = get(id);
        String old = faculty.toString();
        faculty.setName(data.getName());
        faculty.setIsActive(data.getIsActive());
        faculty.setIsDeleted(data.getIsDeleted());

        if (data.getUniversity() != null && data.getUniversity().getId() != null) {
            University university = universityRepository.findById(data.getUniversity().getId())
                    .orElseThrow(() -> new RuntimeException("University not found"));
            faculty.setUniversity(university);
        }

        Faculty updated = repo.save(faculty);
        auditLogService.log("faculties", id, user, "UPDATE", old, updated.toString());
        return updated;
    }

    @Transactional
    public void delete(Long id, User user) {
        Faculty faculty = get(id);
        faculty.setIsDeleted(true);
        repo.save(faculty);
        auditLogService.log("faculties", id, user, "DELETE", faculty.toString(), null);
    }
}
