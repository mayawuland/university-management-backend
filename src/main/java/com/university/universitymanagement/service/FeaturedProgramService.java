package com.university.universitymanagement.service;

import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.university.universitymanagement.utility.PaginationUtils.paginate;

/**
 * Service class for managing FeaturedProgram entities.
 * Handles creation, listing, updating, and deletion of featured programs.
 */
@Service
public class FeaturedProgramService {
    private final FeaturedProgramRepository repo;
    private final UniversityRepository universityRepository;
    private final AuditLogService auditLogService;

    public FeaturedProgramService(FeaturedProgramRepository repo,
                                  UniversityRepository universityRepository,
                                  AuditLogService auditLogService) {
        this.repo = repo;
        this.universityRepository = universityRepository;
        this.auditLogService = auditLogService;
    }

    /**
     * Create a new featured program associated with a university.
     */
    @Transactional
    public FeaturedProgram create(FeaturedProgram featuredProgram, User user) {
        University university = universityRepository.findById(featuredProgram.getUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        featuredProgram.setUniversity(university);
        FeaturedProgram saved = repo.save(featuredProgram);
        auditLogService.log("featured_programs", saved.getUniversity().getId(), user, "CREATE", null, saved.toString());
        return saved;
    }

    /**
     * Retrieve all featured programs (active and not deleted) with pagination.
     */
    public List<FeaturedProgram> all(int page, int size) {
        List<FeaturedProgram> featuredPrograms = repo.findAll().stream()
                .filter(fp -> fp.getIsActive() && !fp.getIsDeleted())
                .toList();
        return paginate(featuredPrograms, page, size);
    }

    /**
     * Get a specific featured program by its ID.
     */
    public FeaturedProgram get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Featured program not found"));
    }

    /**
     * Update the featured program details.
     */
    @Transactional
    public FeaturedProgram update(Long id, FeaturedProgram data, User user) {
        FeaturedProgram existing = get(id);
        University newUniversity = universityRepository.findById(data.getUniversity().getId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        String old = existing.toString();

        existing.setUniversity(newUniversity);
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());
        existing.setIsActive(data.getIsActive());
        existing.setIsDeleted(data.getIsDeleted());

        FeaturedProgram updated = repo.save(existing);
        auditLogService.log("featured_programs", id, user, "UPDATE", old, updated.toString());
        return updated;
    }

    /**
     * Soft delete a featured program.
     */
    @Transactional
    public void delete(Long id, User user) {
        FeaturedProgram featuredProgram = get(id);
        featuredProgram.setIsDeleted(true);
        repo.save(featuredProgram);
        auditLogService.log("featured_programs", id, user, "DELETE", null, null);
    }
}
