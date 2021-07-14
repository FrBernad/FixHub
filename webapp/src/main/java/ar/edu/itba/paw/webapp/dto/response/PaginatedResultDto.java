package ar.edu.itba.paw.webapp.dto.response;

import java.util.Collection;

public class PaginatedResultDto<T> {

    private int page;
    private int totalPages;

    private Collection<T> results;

    public PaginatedResultDto() {
    }

    public PaginatedResultDto(int page, int totalPages, Collection<T> results) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }
}
