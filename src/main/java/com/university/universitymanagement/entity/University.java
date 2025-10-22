package com.university.universitymanagement.entity;

import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Represents a university in the University Management system.
 * 
 * Each university has a name, active status, deletion status, and a list of faculty associated with it.
 */
@Entity
@Table(name = "universities")
public class University {
    /** The unique identifier for the university. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the university. Cannot be blank. */
    @NotBlank private String name;

    /** Indicates whether the university is active. Defaults to true. */
    private Boolean isActive = true;

    /** Indicates whether the university is deleted. Defaults to false. */
    private Boolean isDeleted = false;

    /** List of faculties associated with this university */
    @OneToMany(mappedBy = "university")
    @JsonManagedReference
    private List<Faculty> faculties;

    /** List of featured programs offered by this university */
    @OneToMany(mappedBy = "university")
    @JsonManagedReference
    private List<FeaturedProgram> featuredPrograms;

    /** Getters & Setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public List<Faculty> getFaculties() { return faculties; }
    public void setFaculties(List<Faculty> faculties) { this.faculties = faculties; }

    public List<FeaturedProgram> getFeaturedPrograms() { return featuredPrograms; }
    public void setFeaturedPrograms(List<FeaturedProgram> featuredPrograms) { this.featuredPrograms = featuredPrograms; }
}

