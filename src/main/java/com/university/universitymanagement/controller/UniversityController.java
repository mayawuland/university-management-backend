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
 * Controller for managing universities.
 * 
 * Provides endpoints for creating, reading, updating, and deleting universities,
 * as well as searching universities by name.
 * All endpoints require an Authorization token.
 */
@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;
    private final UserService userService;

    /**
     * Constructor for UniversityController.
     *
     * @param universityService Service for handling university-related operations.
     * @param userService Service for handling user authentication and token validation.
     */
    public UniversityController(UniversityService universityService, UserService userService) {
        this.universityService = universityService;
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
     * Create a new University.
     *
     * @param university The University to create.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the created University and message.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody University university, HttpServletRequest req) {
        try {
            University created = universityService.create(university, getUser(req));
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "University created successfully",
                    "data", created
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to create university",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Fetch all active universities with pagination.
     *
     * @param page Page index (default 0).
     * @param size Page size (default 50).
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing a list of Universities and message.
     */
    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletRequest req) {
        try {
            getUser(req);
            List<University> universities = universityService.all(page, size);
            return ResponseEntity.ok(Map.of(
                    "message", "Universities fetched successfully",
                    "data", universities
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to fetch universities",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Fetch a single Universities by ID.
     *
     * @param id University ID.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the University and message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest req) {
        try {
            getUser(req);
            University university = universityService.get(id);
            return ResponseEntity.ok(Map.of(
                    "message", "University fetched successfully",
                    "data", university
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Failed to fetch university",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Update an existing University by ID.
     *
     * @param id University ID.
     * @param university Updated university data.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the updated University and message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody University university, HttpServletRequest req) {
        try {
            University updated = universityService.update(id, university, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "University updated successfully",
                    "data", updated
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to update university",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Soft delete a University by ID.
     *
     * @param id University ID.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest req) {
        try {
            universityService.delete(id, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "University deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to delete university",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Search universities by name with pagination.
     *
     * @param name Name to search for.
     * @param page Page index (default 0).
     * @param size Page size (default 50).
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the search results and message.
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletRequest req) {
        try {
            getUser(req);
            List<University> results = universityService.searchByName(name, page, size);
            return ResponseEntity.ok(Map.of(
                    "message", "Universities fetched successfully",
                    "data", results
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to search universities",
                    "error", e.getMessage()
            ));
        }
    }
 
}
