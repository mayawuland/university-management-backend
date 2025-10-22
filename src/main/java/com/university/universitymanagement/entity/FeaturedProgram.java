package com.university.universitymanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a featured program in the University Management System.
 *
 * A featured program highlights a specific department program 
 * that should be visible across all universities regardless of faculty.
 */
@Entity
@Table(name = "featured_programs")
public class FeaturedProgram {

    /** Unique identifier for the featured program. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The title or name of the featured program. Cannot be blank. */
    @NotBlank
    private String title;

    /** A brief description of the featured program. */
    private String description;

    /** Indicates whether the featured program is currently active. Defaults to true. */
    private Boolean isActive = true;

    /** Indicates whether the featured program is deleted. Defaults to false. */
    private Boolean isDeleted = false;

    /**
     * The department associated with this featured program.
     * Back reference for JSON serialization to prevent infinite recursion.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    @JsonBackReference
    private University university;

    /** Getters & Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }
}
