package com.noormasjid.controller;

import com.noormasjid.entity.content.ContentPage;
import com.noormasjid.security.UserDetailsImpl;
import com.noormasjid.service.ContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-pages")
public class ContentPageController {

    private final ContentService contentService;

    public ContentPageController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<ContentPage>> getPublished() {
        return ResponseEntity.ok(contentService.getPublishedPages());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ContentPage> getBySlug(@PathVariable String slug) {
        ContentPage page = contentService.getBySlug(slug);
        if (page == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ContentPage> create(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody ContentPage page) {
        return ResponseEntity.ok(contentService.createPage(page, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentPage> update(
            @PathVariable Long id,
            @RequestBody ContentPage page) {
        return ResponseEntity.ok(contentService.updatePage(id, page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        contentService.deletePage(id);
        return ResponseEntity.ok().build();
    }
}
