package com.learnspringboot.ppmtool.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Project name is required")
  private String projectName;
  @NotBlank(message = "Project identifier is required")
  @Size(min = 4, max = 5, message = "Project identifier must be 4 or 5 characters")
  @Column(updatable = false, unique = true)
  private String projectIdentifier;
  @NotBlank(message = "Project description is required")
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date start_date;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date end_date;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date created_at;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date updated_at;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
  @JsonIgnore
  private Backlog backlog;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private User user;

  private String projectLeader;

  @PrePersist
  public void onCreate() {
    this.created_at = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    this.updated_at = new Date();
  }

}