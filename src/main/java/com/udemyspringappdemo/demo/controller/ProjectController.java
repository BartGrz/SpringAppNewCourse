package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.logic.ProjectService;
import com.udemyspringappdemo.demo.model.ProjectStep;
import com.udemyspringappdemo.demo.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    String addProject (@ModelAttribute("project")ProjectWriteModel current,Model model) {
        projectService.save(current.toProject());
        model.addAttribute("project",new ProjectWriteModel());
        model.addAttribute("message","project added");
        return "projects";
    }
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project")ProjectWriteModel current) {
        current.getSteps().add(new ProjectStep());
        return "projects";

    }


}
