package com.university.universitymanagement.repository;

import com.university.universitymanagement.entity.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Featured Program entity.
 * 
 * Provides standard CRUD operations and query methods for FeaturedProgram.
 * 
 */
public interface FeaturedProgramRepository extends JpaRepository<FeaturedProgram, Long> {
    //boolean existsByDepartment(Department department);

    List<FeaturedProgram> findByUniversityAndIsActiveTrueAndIsDeletedFalse(University university);
}
