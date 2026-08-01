package com.noormasjid.service;

import com.noormasjid.entity.content.ContentPage;
import com.noormasjid.repository.ContentPageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContentService {

    private final ContentPageRepository contentPageRepository;

    public ContentService(ContentPageRepository contentPageRepository) {
        this.contentPageRepository = contentPageRepository;
    }

    public List<ContentPage> getPublishedPages() {
        return contentPageRepository.findByIsDraftAndIsDeleted(false, 0);
    }

    public ContentPage getBySlug(String slug) {
        return contentPageRepository.findBySlugAndIsDeleted(slug, 0).orElse(null);
    }

    public ContentPage createPage(ContentPage page, Long authorId) {
        return contentPageRepository.save(page);
    }

    public ContentPage updatePage(Long id, ContentPage page) {
        ContentPage existing = contentPageRepository.findById(id).orElseThrow();
        existing.setTitle(page.getTitle());
        existing.setBody(page.getBody());
        existing.setContentType(page.getContentType());
        existing.setSummaryAi(page.getSummaryAi());
        existing.setIsDraft(page.getIsDraft());
        if (!page.getIsDraft()) {
            existing.setPublishedAt(LocalDateTime.now());
        }
        return contentPageRepository.save(existing);
    }

    public void deletePage(Long id) {
        ContentPage p = contentPageRepository.findById(id).orElseThrow();
        p.setIsDeleted(1);
        contentPageRepository.save(p);
    }
}
