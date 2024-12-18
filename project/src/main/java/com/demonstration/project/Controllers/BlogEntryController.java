package com.demonstration.project.Controllers;

import com.demonstration.project.Entities.BlogEntry;
import com.demonstration.project.Services.BlogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/blog-entries")
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;

    @GetMapping
    public ResponseEntity<List<BlogEntry>> getAllBlogEntries() {
        List<BlogEntry> blogEntries = blogEntryService.getAllBlogEntries();
        return ResponseEntity.ok(blogEntries);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogEntry> getBlogEntryById(@PathVariable UUID blogId) {
        Optional<BlogEntry> blogEntry = blogEntryService.getBlogEntryById(blogId);
        return blogEntry.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogEntry>> getBlogEntriesByUser(@PathVariable UUID userId) {
        List<BlogEntry> blogEntries = blogEntryService.getBlogEntriesByUser(userId);
        return blogEntries.isEmpty() 
               ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() 
               : ResponseEntity.ok(blogEntries);
    }

    @PostMapping
    public ResponseEntity<BlogEntry> createBlogEntry(@Valid @RequestBody BlogEntry blogEntry) {
        BlogEntry createdBlogEntry = blogEntryService.saveBlogEntry(blogEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlogEntry);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogEntry> updateBlogEntry(@PathVariable UUID blogId, 
        @Valid @RequestBody BlogEntry updatedBlogEntry) {
        try {
            BlogEntry updated = blogEntryService.updateBlogEntry(blogId, updatedBlogEntry);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlogEntry(@PathVariable UUID blogId) {
        try {
            blogEntryService.deleteBlogEntry(blogId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogEntry>> searchBlogEntriesByTitle(@RequestParam String title) {
        List<BlogEntry> blogEntries = blogEntryService.searchBlogEntriesByTitle(title);
        return blogEntries.isEmpty() 
               ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() 
               : ResponseEntity.ok(blogEntries);
    }
}
