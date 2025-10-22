package com.university.universitymanagement.controller;

import com.university.universitymanagement.entity.*;
import com.university.universitymanagement.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Featured Programs.
 * 
 * Provides endpoints for creating, retrieving, updating, and deleting Featured Programs.
 * All endpoints require authentication via the Authorization header.
 */
@RestController
@RequestMapping("/api/featured-programs")
public class FeaturedProgramController {

    private final FeaturedProgramService featuredProgramService;
    private final UserService userService;

    /**
     * Constructor for FeaturedProgramController.
     *
     * @param featuredProgramService Service for handling Featured Program-related operations.
     * @param userService Service for handling user authentication and token validation.
     */
    public FeaturedProgramController(FeaturedProgramService featuredProgramService, UserService userService) {
        this.featuredProgramService = featuredProgramService;
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
     * Create a new Featured Program.
     *
     * @param featuredProgram The FeaturedProgram to create.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the created FeaturedProgram and message.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FeaturedProgram featuredProgram, HttpServletRequest req) {
        try {
            FeaturedProgram saved = featuredProgramService.create(featuredProgram, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Featured Program created successfully",
                    "data", saved
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to create Featured Program",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Retrieve all Featured Programs with pagination.
     *
     * @param page Page number (default 0).
     * @param size Page size (default 50).
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the list of Featured Programs and message.
     */
    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            HttpServletRequest req) {
        try {
            getUser(req);
            List<FeaturedProgram> list = featuredProgramService.all(page, size);
            return ResponseEntity.ok(Map.of(
                    "message", "Featured Programs retrieved successfully",
                    "data", list
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to retrieve Featured Programs",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Update an existing Featured Program by ID.
     *
     * @param id The ID of the Featured Program to update.
     * @param featuredProgram The updated FeaturedProgram data.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing the updated FeaturedProgram and message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody FeaturedProgram featuredProgram,
            HttpServletRequest req
    ) {
        try {
            FeaturedProgram updated = featuredProgramService.update(id, featuredProgram, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Featured Program updated successfully",
                    "data", updated
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to update Featured Program",
                    "error", e.getMessage()
            ));
        }
    }

    /**
     * Soft delete a Featured Program by ID.
     *
     * @param id The ID of the FeaturedProgram to delete.
     * @param req The HTTP request containing the Authorization header.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest req) {
        try {
            featuredProgramService.delete(id, getUser(req));
            return ResponseEntity.ok(Map.of(
                    "message", "Featured Program deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Failed to delete Featured Program",
                    "error", e.getMessage()
            ));
        }
    }
}
