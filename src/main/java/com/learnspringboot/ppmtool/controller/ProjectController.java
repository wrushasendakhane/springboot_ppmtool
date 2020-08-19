package com.learnspringboot.ppmtool.controller;

import java.security.Principal;

import javax.validation.Valid;

import com.learnspringboot.ppmtool.domain.Project;
import com.learnspringboot.ppmtool.services.MapValidationErrorService;
import com.learnspringboot.ppmtool.services.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private MapValidationErrorService mapValidationErrorService;

  // @RequestMapping(method = RequestMethod.POST)
  @PostMapping("")
  public ResponseEntity<?> add(@Valid @RequestBody Project project, BindingResult result, Principal principal) {

    System.out.println(principal.getName());
    if (result.hasErrors()) {
      return mapValidationErrorService.MapValidationService(result);
    }

    return ResponseEntity.ok().body(projectService.saveOrUpdate(project, principal.getName()));
  }

  @GetMapping(path = "/{projectId}")
  public ResponseEntity<?> findByProjectId(@PathVariable String projectId, Principal principal) {

    Project project = projectService.findByProjectIdentifier(projectId, principal.getName());
    return ResponseEntity.ok().body(project);
  }

  @GetMapping(path = "/all")
  public Iterable<Project> findAllProjects(Principal principal) {
    return projectService.findAllProjects(principal.getName());
  }

  @DeleteMapping(path = "/{projectId}")
  public ResponseEntity<?> deleteByProjectId(@PathVariable String projectId, Principal principal) {
    projectService.deleteByProjectIdentifier(projectId, principal.getName());
    return ResponseEntity.ok().body("Project '" + projectId + "' deleted successfully");
  }
}