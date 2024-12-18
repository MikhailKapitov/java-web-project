package com.demonstration.project.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Table;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity @AllArgsConstructor @Getter @Setter
@Table(name = "\"users\"")
public class User{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank(message = "A username is required")
    private String username;
    // Could use an @Pattern for a more thorough RegEx check.

}
