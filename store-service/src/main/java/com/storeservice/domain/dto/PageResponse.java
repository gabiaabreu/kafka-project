package com.storeservice.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private PageMetadata page;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = new PageMetadata(page);
    }

    @Data
    public static class PageMetadata {
        private int pageNumber;
        private int pageSize;
        private long numberOfElements;
        private long totalElements;
        private int totalPages;

        public PageMetadata(Page<?> page) {
            this.pageNumber = page.getNumber();
            this.pageSize = page.getSize();
            this.numberOfElements = page.getNumberOfElements();
            this.totalElements = page.getTotalElements();
            this.totalPages = page.getTotalPages();
        }
    }
}
