package com.udemyspringappdemo.demo.model.projection;

import com.udemyspringappdemo.demo.model.Project;
import com.udemyspringappdemo.demo.model.ProjectStep;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    @NotBlank(message = "Project's description must not be empty")
    @Getter
    @Setter
    private String description ;
    @Valid
    @Getter
    @Setter
    private List<ProjectStep> steps ;

    public Project toProject() {
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }

}
