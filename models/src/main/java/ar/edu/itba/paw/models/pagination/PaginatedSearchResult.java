package ar.edu.itba.paw.models.pagination;

import java.util.Collection;

public class PaginatedSearchResult<T> {

    private final boolean isLast, isFirst;
    private int page;
    private int itemsPerPage;
    private int totalItems;
    private final int totalPages;

    private Collection<T> results;

    public PaginatedSearchResult(int page, int itemsPerPage, int totalItems, Collection<T> results) {
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.results = results;
        this.totalPages = (int) Math.ceil((float) totalItems / itemsPerPage);
        this.isFirst = page == 0;
        this.isLast = itemsPerPage * page + results.size() > itemsPerPage * (totalPages - 1) && itemsPerPage * page + results.size() <= itemsPerPage * (totalPages);
    }
    public boolean isLast() {
        return isLast;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int setTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Collection<T> getResults() {
        return results;
    }

    public int getLastPage() {
        return totalPages-1;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }
}