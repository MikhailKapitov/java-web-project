package com.demonstration.project.Services;

import com.demonstration.project.Entities.BlogEntry;
import com.demonstration.project.Repositories.BlogEntryRepository;
import com.demonstration.project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogEntryService {

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public BlogEntry saveBlogEntry(BlogEntry blogEntry) {
        if (userRepository.existsById(blogEntry.getUser().getId())) {
            return blogEntryRepository.save(blogEntry);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public List<BlogEntry> getAllBlogEntries() {
        return blogEntryRepository.findAll();
    }

    public Optional<BlogEntry> getBlogEntryById(UUID blogId) {
        return blogEntryRepository.findById(blogId);
    }

    public List<BlogEntry> getBlogEntriesByUser(UUID userId) {
        return blogEntryRepository.findByUserId(userId);
    }

    public List<BlogEntry> searchBlogEntriesByTitle(String title) {
        return blogEntryRepository.findByTitleContaining(title);
    }

    @Transactional
    public BlogEntry updateBlogEntry(UUID blogId, BlogEntry updatedBlogEntry) {
        Optional<BlogEntry> existingBlogEntry = blogEntryRepository.findById(blogId);
        if (existingBlogEntry.isPresent()) {
            updatedBlogEntry.setBlogId(blogId);
            return blogEntryRepository.save(updatedBlogEntry);
        }
        throw new IllegalArgumentException("Blog entry not found");
    }

    @Transactional
    public void deleteBlogEntry(UUID blogId) {
        blogEntryRepository.deleteById(blogId);
    }
}
