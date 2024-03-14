package com.books.librarymanagementsystem.bo;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

  public static final String DATE_FORMAT_UTC_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  @Id
  @GeneratedValue
  private Long id;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_UTC_TIMEZONE, timezone = "IST")
  private Date createdDate;

  @LastModifiedDate
  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_UTC_TIMEZONE, timezone = "IST")
  private Date updatedDate;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String updatedBy;
}
