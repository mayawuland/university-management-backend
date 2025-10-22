package com.university.universitymanagement.repository;

import com.university.universitymanagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for faculty entity.
 * 
 * Provides standard CRUD operations and query methods for faculty.
 * 
 * Additionally, this repository defines a custom query method:
 * {@link #findByIsActiveTrueAndIsDeletedFalse()} - returns a list of faculties
 * that are active and not deleted.
 */
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByIsActiveTrueAndIsDeletedFalse();
}
