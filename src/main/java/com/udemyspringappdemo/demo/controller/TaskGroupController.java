package com.udemyspringappdemo.demo.controller;

import com.udemyspringappdemo.demo.logic.TaskGroupService;
import com.udemyspringappdemo.demo.model.Task;
import com.udemyspringappdemo.demo.model.TaskRepository;
import com.udemyspringappdemo.demo.model.projection.GroupReadModel;
import com.udemyspringappdemo.demo.model.projection.GroupTaskWriteModel;
import com.udemyspringappdemo.demo.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Controller
//zmiana adnotacji z @RepositoryRestController na RestController [wystarczyłby @controller] (teraz mamy tylko odczyt tasków)
@RequestMapping("/groups")
public class TaskGroupController {

    public static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskRepository repository;
    private final TaskGroupService taskGroupService;

    TaskGroupController(final TaskRepository repository, TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupService = taskGroupService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups(Pageable pageable) {
        return ResponseEntity.ok(taskGroupService.readAll());

    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {

        GroupReadModel result = taskGroupService.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @ResponseBody
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toogleGroup(@PathVariable int id) {
        taskGroupService.toogleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups" ;
        }
        taskGroupService.createGroup(current); 
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "group added");
        return "groups" ;
    }

    @ModelAttribute("groups")
    public List<GroupReadModel> getGroups() {
       return taskGroupService.readAll();
    }

    @PostMapping(params = "addStep", produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";

    }


    /**
     * Exception handler will take care of exception if it was thrown
     *
     * @param e
     * @return 404 status , because id was not found
     */
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Exception handler will take care of exception if it was thrown
     *
     * @param e
     * @return 500 status - if group has undone tasks
     */
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
