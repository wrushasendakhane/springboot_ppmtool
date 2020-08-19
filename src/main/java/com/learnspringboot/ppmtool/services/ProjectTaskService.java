package com.learnspringboot.ppmtool.services;

import com.learnspringboot.ppmtool.domain.Backlog;
import com.learnspringboot.ppmtool.domain.Project;
import com.learnspringboot.ppmtool.domain.ProjectTask;
import com.learnspringboot.ppmtool.exceptions.ProjectNotFoundException;
import com.learnspringboot.ppmtool.repositories.BacklogRepository;
import com.learnspringboot.ppmtool.repositories.ProjectRepository;
import com.learnspringboot.ppmtool.repositories.ProjectTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProjectTaskService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private BacklogRepository backlogRepository;

  @Autowired
  private ProjectTaskRepository projectTaskRepository;

  @Autowired
  private ProjectService projectService;

  public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

    try {

      // Backlog backlog =
      // backlogRepository.findByProjectIdentifier(projectIdentifier);
      Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();

      Integer PTSequence = backlog.getPTSequence();
      PTSequence++;
      backlog.setPTSequence(PTSequence);

      projectTask.setBacklog(backlog);
      projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + PTSequence);
      projectTask.setProjectIdentifier(backlog.getProjectIdentifier());

      if (StringUtils.isEmpty(projectTask.getStatus())) {
        projectTask.setStatus("TO_DO");
      }

      if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
        projectTask.setPriority(3);
      }

      return projectTaskRepository.save(projectTask);
    } catch (Exception e) {
      throw new ProjectNotFoundException("Project '" + projectIdentifier + "' Not Found");
    }

  }

  public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username) {
    Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
    if (project == null) {
      throw new ProjectNotFoundException("Project '" + projectIdentifier + "' doesn't exists");
    }
    return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
  }

  public ProjectTask findPTByProjectSequence(String projectIdentifier, String projectSequence, String username) {
    // Backlog backlog =
    // backlogRepository.findByProjectIdentifier(projectIdentifier);
    // if (backlog == null) {
    // throw new ProjectNotFoundException("Project '" + projectIdentifier + "' not
    // found");
    // }
    projectService.findByProjectIdentifier(projectIdentifier, username);

    ProjectTask projectTask = projectTaskRepository.findByProjectIdentifierAndProjectSequence(projectIdentifier,
        projectSequence);
    if (projectTask == null) {
      throw new ProjectNotFoundException(
          "Project Task '" + projectSequence + "' not found in project '" + projectIdentifier + "'");
    }
    // projectTask.setBacklog(backlog);
    return projectTask;
  }

  public ProjectTask updateProjectTask(ProjectTask updateTask, String projectIdentifier, String projectSequence,
      String username) {
    ProjectTask projectTask = findPTByProjectSequence(projectIdentifier, projectSequence, username);
    updateTask.setBacklog(projectTask.getBacklog());
    updateTask.setProjectIdentifier(projectIdentifier);
    updateTask.setProjectSequence(projectSequence);
    updateTask.setId(projectTask.getId());
    projectTask = updateTask;
    return projectTaskRepository.save(projectTask);
  }

  public void deleteProjectTask(String projectIdentifier, String projectSequence, String username) {
    ProjectTask projectTask = findPTByProjectSequence(projectIdentifier, projectSequence, username);
    // Backlog backlog = projectTask.getBacklog();
    // backlog.getProjectTasks().remove(projectTask);
    projectTaskRepository.delete(projectTask);
  }
}