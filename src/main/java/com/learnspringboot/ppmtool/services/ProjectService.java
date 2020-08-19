package com.learnspringboot.ppmtool.services;

import com.learnspringboot.ppmtool.domain.Backlog;
import com.learnspringboot.ppmtool.domain.Project;
import com.learnspringboot.ppmtool.domain.User;
import com.learnspringboot.ppmtool.exceptions.ProjectIdException;
import com.learnspringboot.ppmtool.exceptions.ProjectNotFoundException;
import com.learnspringboot.ppmtool.repositories.BacklogRepository;
import com.learnspringboot.ppmtool.repositories.ProjectRepository;
import com.learnspringboot.ppmtool.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private BacklogRepository backlogRepository;

  @Autowired
  private UserRepository userRepository;

  public Project saveOrUpdate(Project project, String username) {

    if (project.getId() != null && project.getId() > 0) {
      Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
      if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
        throw new ProjectNotFoundException("Project not found in you account");
      } else if (existingProject == null) {
        throw new ProjectNotFoundException(
            "Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it doesn't exists");
      }
    }

    try {
      User user = userRepository.findByUsername(username);
      project.setUser(user);
      project.setProjectLeader(user.getUsername());
      String projectIdentifier = project.getProjectIdentifier().toUpperCase();
      project.setProjectIdentifier(projectIdentifier);
      if (project.getId() == null) {
        Backlog backlog = new Backlog();
        backlog.setProjectIdentifier(projectIdentifier);
        backlog.setProject(project);
        project.setBacklog(backlog);
      }

      if (project.getId() != null) {
        project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
      }

      return projectRepository.save(project);
    } catch (Exception ex) {
      throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
    }
  }

  public Project findByProjectIdentifier(String projectId, String username) {
    Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    if (project == null) {
      throw new ProjectIdException("Project ID '" + projectId + "' doest not exist");
    }
    if (!project.getProjectLeader().equals(username)) {
      throw new ProjectNotFoundException("Project not found in you account");
    }
    return project;
  }

  public Iterable<Project> findAllProjects(String username) {
    return projectRepository.findAllByProjectLeader(username);
  }

  public void deleteByProjectIdentifier(String projectId, String username) {

    projectRepository.delete(findByProjectIdentifier(projectId, username));
  }
}