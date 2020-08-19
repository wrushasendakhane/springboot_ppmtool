package com.learnspringboot.ppmtool.repositories;

import com.learnspringboot.ppmtool.domain.Backlog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

  Backlog findByProjectIdentifier(String projectIdentifier);

}