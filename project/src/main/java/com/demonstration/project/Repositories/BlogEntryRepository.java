package com.demonstration.project.Repositories;

import com.demonstration.project.Entities.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface BlogEntryRepository extends JpaRepository<BlogEntry, UUID> {
    List<BlogEntry> findByUserId(UUID userId);
    List<BlogEntry> findByTitleContaining(String title);
}
