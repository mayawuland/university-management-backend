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
 * REST controller for managing Faculty entities.
 * Provides endpoints to create, read, update, and delete faculties.
 * All endpoints require a valid Authorization token passed in the header.
 */
@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final UserService userService;

    /**
     * Constructor for FacultyController.
     *
     * @param facultyService Service for handling faculty-related operations.
     * @param userService Service for handling user authentication and token validation.
     */
    public FacultyController(FacultyService facultyService, UserService userService) {
        this.facultyService = facultyService;
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
     * Create a new faculty.
     *
     * @param faculty Faculty object containing faculty data.
     * @param req HTTP request for user authentication.
     * @return ResponseEntity with created faculty data or error message.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Faculty faculty, HttpServletRequest req) {
        try {
            Faculty created = facultyService.create(faculty, getUser(req));
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Faculty created successfully",
                    "data", created
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to create faculty",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Get all active faculties with pagination.
     *
     * @param page Page number (default 0)
     * @param size Page size (default 50)
     * @param req HTTP request for user authentication.
     * @return ResponseEntity containing a list of faculties or error message.
     */
    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletRequest req) {
        try {
            getUser(req);
            List<Faculty> faculties = facultyService.all(page, size);
            return ResponseEntity.ok(Map.of(
                    "message", "Faculties fetched successfully",
                    "data", faculties
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to fetch faculties",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Get a faculty by its ID.
     *
     * @param id Faculty ID
     * @param req HTTP request for user authentication.
     * @return ResponseEntity containing the faculty data or error message if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest req) {
        try {
            getUser(req);
            Faculty faculty = facultyService.get(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Faculty fetched successfully",
                    "data", faculty
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Failed to fetch faculty",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Update an existing faculty.
     *
     * @param id Faculty ID
     * @param faculty Faculty object with updated data.
     * @param req HTTP request for user authentication.
     * @return ResponseEntity containing updated faculty data or error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Faculty faculty, HttpServletRequest req) {
        try {
            Faculty updated = facultyService.update(id, faculty, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Faculty updated successfully",
                    "data", updated
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to update faculty",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Soft delete a faculty by setting its isDeleted flag to true.
     *
     * @param id Faculty ID
     * @param req HTTP request for user authentication.
     * @return ResponseEntity with a success message or error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest req) {
        try {
            facultyService.delete(id, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Faculty deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to delete faculty",
                    "error", e.getMessage()
            ));
        }
    }

}


