package com.university.universitymanagement.controller;

import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Departments.
 * 
 * Provides endpoints for creating, retrieving, updating, and deleting departments.
 * All endpoints require authentication via the Authorization header.
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final UserService userService;

    /**
     * Constructor for DepartmentController.
     *
     * @param departmentService Service for handling department-related operations.
     * @param userService Service for handling user authentication and token validation.
     */
    public DepartmentController(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }

    /**
     * Helper method to retrieve the authenticated user from the Authorization header.
     *
     * @param req The HTTP request containing the Authorization header.
     * @return Authenticated User object.
     */
    private User getUser(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        
        if (token == null || token.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        return userService.findByToken(token);
    }

    /**
     * Create a new department.
     *
     * @param department The department to create.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the created department and message.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Department department, HttpServletRequest req) {
        try {
            Department created = departmentService.create(department, getUser(req));
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Department created successfully",
                    "data", created
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to create department",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Retrieve all Departments with pagination.
     *
     * @param page Page number (default 0).
     * @param size Page size (default 50).
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the list of departments and pagination info.
     */
    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletRequest req) {
        try {
            getUser(req);
            List<Department> departments = departmentService.all(page, size);
            return ResponseEntity.ok(Map.of(
                    "message", "Departments fetched successfully",
                    "data", departments
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to fetch departments",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Retrieve a single department by ID.
     *
     * @param id The ID of the department to retrieve.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the department and message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest req) {
        try {
            getUser(req);
            Department department = departmentService.get(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Department fetched successfully",
                    "data", department
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Failed to fetch department",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Update an existing department by ID.
     *
     * @param id The ID of the department to update.
     * @param department The updated department data.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the updated department and message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Department department, HttpServletRequest req) {
        try {
            Department updated = departmentService.update(id, department, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Department updated successfully",
                    "data", updated
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to update department",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Soft delete a Department by ID.
     *
     * @param id The ID of the department to delete.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest req) {
        try {
            departmentService.delete(id, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Department deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to delete department",
                    "error", e.getMessage()
            ));
        }
    }
    
}


