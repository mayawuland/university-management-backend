package com.university.universitymanagement.repository;

import com.university.universitymanagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//import java.util.Optional;

/**
 * Repository interface for Store entity.
 * 
 * Provides standard CRUD operations and query methods for Store.
 * 
 * Additionally, this repository defines a custom query method:
 * {@link #findByIsActiveTrueAndIsDeletedFalse()} - returns a list of stores
 * that are active and not deleted.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByIsActiveTrueAndIsDeletedFalse();

    //Optional<Department> findByNameContainingIgnoreCaseAndIsActiveTrueAndIsDeletedFalse(String universityName);
}
