package com.university.universitymanagement.repository;

import com.university.universitymanagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for University entity.
 * 
 * Provides standard CRUD operations and query methods for Province.
 * 
 * Additionally, this repository defines custom query methods:
 * {@link #findByIsActiveTrueAndIsDeletedFalse()} - returns all active universities that are not deleted.
 * {@link #findByNameContainingIgnoreCaseAndIsActiveTrueAndIsDeletedFalse(String)} - returns all active and not deleted universities whose names contain the given string, ignoring case.
 */
public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findByIsActiveTrueAndIsDeletedFalse();
    List<University> findByNameContainingIgnoreCaseAndIsActiveTrueAndIsDeletedFalse(String name);
}
