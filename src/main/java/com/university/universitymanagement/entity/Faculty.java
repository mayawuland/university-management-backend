package com.university.universitymanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Represents a faculty within a universiity in the University Management system.
 * 
 * Each faculty belongs to one university and can have multiple department associated with it.
 */
@Entity
@Table(name = "faculties")
public class Faculty {
    /** The unique identifier for the faculty. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the faculty. Cannot be blank. */
    @NotBlank private String name;

    /** Indicates whether the faculty is active. Defaults to true. */
    private Boolean isActive = true;

    /** Indicates whether the faculty is deleted. Defaults to false. */
    private Boolean isDeleted = false;

    /**
     * The university this faculty belongs to.
     * Back reference for JSON serialization to prevent infinite recursion.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    @JsonBackReference 
    private University university;

    /** List of departments associated with this faculty */
    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private List<Department> departments;

    /** Getters & Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }

    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }
}

