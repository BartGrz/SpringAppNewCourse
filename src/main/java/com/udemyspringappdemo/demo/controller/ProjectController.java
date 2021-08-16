package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.logic.ProjectService;
import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.ProjectStep;
import com.udemyspringappdemo.demo.model.projection.ProjectWriteModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }
    @PostMapping()
    String addProject (
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            return "projects";
        }
        projectService.save(current.toProject());
        model.addAttribute("project",new ProjectWriteModel());
        model.addAttribute("projects",getProjects());
        model.addAttribute("message","project added");
        return "projects";
    }
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project")ProjectWriteModel current) {
        current.getSteps().add(new ProjectStep());
        return "projects";

    }
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project")ProjectWriteModel current,
            Model model,
            @PathVariable int id,
           @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            projectService.createGroup(id,deadline);
            model.addAttribute("message","group added");
        }catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message","error while trying to create group");
        }
        return "projects";

    }
    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.readAll();
    }

}
