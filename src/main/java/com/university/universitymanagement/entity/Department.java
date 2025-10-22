package com.university.universitymanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a department within a faculty in the University Management system.
 * 
 * Each department belongs to one faculty.
 */
@Entity
@Table(name = "departments")
public class Department {
    /** The unique identifier for the department. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the department. Cannot be blank. */
    @NotBlank private String name;

    /** The location of the department. Cannot be blank. */
    @NotBlank private String location;

    /** Indicates whether the department is active. Defaults to true. */
    private Boolean isActive = true;

    /** Indicates whether the department is deleted. Defaults to false. */
    private Boolean isDeleted = false;

     /**
     * The Faculty this department belongs to.
     * Back reference for JSON serialization to prevent infinite recursion.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonBackReference
    private Faculty faculty;

    /** Getters & Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
}

