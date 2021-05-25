package com.udemyspringappdemo.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    @Setter
    @NotBlank(message = "Project's description must not be empty")
    private String description;

    @Getter
    @Setter
    @OneToMany( cascade = CascadeType.ALL,mappedBy = "project")
    private Set<ProjectStep> steps;

    public Project() {
    }

}
