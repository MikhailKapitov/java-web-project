package com.demonstration.project.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "\"blog_entries\"")
public class BlogEntry {

    @Id
    @GeneratedValue
    private UUID blogId; 

    @ManyToOne
    private User user;

    private String title;

    private String text;

    public BlogEntry() {}
}
