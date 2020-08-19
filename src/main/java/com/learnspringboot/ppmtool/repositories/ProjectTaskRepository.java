package com.learnspringboot.ppmtool.repositories;

import java.util.List;

import com.learnspringboot.ppmtool.domain.ProjectTask;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

  List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);

  ProjectTask findByProjectIdentifierAndProjectSequence(String projectIdentifier, String projectSequence);
}