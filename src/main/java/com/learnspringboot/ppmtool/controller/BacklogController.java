package com.learnspringboot.ppmtool.controller;

import java.security.Principal;

import javax.validation.Valid;

import com.learnspringboot.ppmtool.domain.ProjectTask;
import com.learnspringboot.ppmtool.repositories.ProjectTaskRepository;
import com.learnspringboot.ppmtool.services.MapValidationErrorService;
import com.learnspringboot.ppmtool.services.ProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping(path = "/api/backlog")
@RestController
public class BacklogController {

  @Autowired
  private ProjectTaskService projectTaskService;

  @Autowired
  private MapValidationErrorService validationErrorService;

  @PostMapping(path = "/{projectIdentifier}")
  public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
      @PathVariable String projectIdentifier, Principal principal) {

    if (result.hasErrors()) {
      return validationErrorService.MapValidationService(result);
    }

    projectTask = projectTaskService.addProjectTask(projectIdentifier, projectTask, principal.getName());
    return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.CREATED);
  }

  @GetMapping(path = "/{projectIdentifier}")
  public Iterable<ProjectTask> getBacklogByProjectIdentifier(@PathVariable String projectIdentifier,
      Principal principal) {

    return projectTaskService.findBacklogByProjectIdentifier(projectIdentifier, principal.getName());
  }

  @GetMapping(path = "/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<?> getProjectTaskByProjectSequence(@PathVariable String projectIdentifier,
      @PathVariable String projectSequence, Principal principal) {
    ProjectTask projectTask = projectTaskService.findPTByProjectSequence(projectIdentifier, projectSequence,
        principal.getName());
    return ResponseEntity.ok(projectTask);
  }

  @PatchMapping(path = "/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
      @PathVariable String projectIdentifier, @PathVariable String projectSequence, Principal principal) {
    if (result.hasErrors()) {
      return validationErrorService.MapValidationService(result);
    }
    projectTask = projectTaskService.updateProjectTask(projectTask, projectIdentifier, projectSequence,
        principal.getName());
    return ResponseEntity.ok().body(projectTask);
  }

  @DeleteMapping(path = "/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier,
      @PathVariable String projectSequence, Principal principal) {
    projectTaskService.deleteProjectTask(projectIdentifier, projectSequence, principal.getName());
    return ResponseEntity.ok()
        .body("Project Task '" + projectSequence + "' deleted successfully from Project '" + projectIdentifier + "'");
  }
}