package com.university.universitymanagement.repository;

import com.university.universitymanagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for AuditLog entity.
 * 
 * Provides standard CRUD operations and query methods for AuditLog.
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
