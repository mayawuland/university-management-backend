package com.university.universitymanagement.service;

import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import static com.university.universitymanagement.utility.PaginationUtils.paginate;
import java.util.List;

/**
 * Service class for managing University entities.
 * Provides functionality to create, read, update, delete (soft delete),
 * and search universities. Also allows searching departments by university
 * including featured programs visible across all universities.
 */
@Service
public class UniversityService {
    private final UniversityRepository repo;
    private final AuditLogService auditLogService;

    public UniversityService(UniversityRepository repo, AuditLogService auditLogService) {
        this.repo = repo;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public University create(University university, User user) {
        University saved = repo.save(university);
        auditLogService.log("universities", saved.getId(), user, "CREATE", null, saved.toString());
        return saved;
    }

    public List<University> all(int page, int size) {
        List<University> universities = repo.findByIsActiveTrueAndIsDeletedFalse();
        return paginate(universities, page, size);
    }

    public University get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Transactional
    public University update(Long id, University data, User user) {
        University university = get(id);
        String old = university.toString();
        university.setName(data.getName());
        university.setIsActive(data.getIsActive());
        university.setIsDeleted(data.getIsDeleted());
        University updated = repo.save(university);
        auditLogService.log("universities", id, user, "UPDATE", old, updated.toString());
        return updated;
    }

    @Transactional
    public void delete(Long id, User user) {
        University university = get(id);
        university.setIsDeleted(true);
        repo.save(university);
        auditLogService.log("universities", id, user, "DELETE", university.toString(), null);
    }

    public List<University> searchByName(String name, int page, int size) {
        List<University> universities = repo.findByNameContainingIgnoreCaseAndIsActiveTrueAndIsDeletedFalse(name);
        return paginate(universities, page, size);
    }

}
