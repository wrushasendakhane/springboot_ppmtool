package com.learnspringboot.ppmtool.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class ProjectTask {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(updatable = false)
  private String projectSequence;

  @NotBlank(message = "Please include project summary")
  private String summary;

  private String acceptanceCriteria;

  private String status;

  private Integer priority;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date dueDate;

  private String projectIdentifier;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date created_At;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date updated_At;

  @PrePersist
  protected void onCreate() {
    this.created_At = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated_At = new Date();
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
  @JsonIgnore
  private Backlog backlog;
}