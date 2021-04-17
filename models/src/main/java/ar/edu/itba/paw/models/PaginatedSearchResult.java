package ar.edu.itba.paw.models;

import java.util.Collection;

public class PaginatedSearchResult<T> {

    private String order, filter, query;
    private boolean isLast, isFirst;
    private int page, itemsPerPage, totalItems, totalPages;

    private Collection<T> results;

    public PaginatedSearchResult(String order, String filter, String query, int page, int itemsPerPage, int totalItems, Collection<T> results) {
        this.order = order;
        this.filter = filter;
        this.query = query;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.results = results;
        this.totalPages = (int)Math.ceil((float)totalItems/itemsPerPage);
        this.isFirst=page==0;
        this.isLast=itemsPerPage*page+results.size()>itemsPerPage*(totalPages-1) && itemsPerPage*page+results.size()<=itemsPerPage*(totalPages);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

    public int setTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }
}