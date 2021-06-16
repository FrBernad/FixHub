package ar.edu.itba.paw.models.pagination;

import ar.edu.itba.paw.models.location.City;

import java.util.Collection;

public class PaginatedSearchResult<T> {

    private String order, category, query, state, city;
    private Collection<City> cities;
    private final boolean isLast, isFirst;
    private int page;
    private int itemsPerPage;
    private int totalItems;
    private final int totalPages;

    private Collection<T> results;

    public PaginatedSearchResult(String order, String category, String query, int page, int itemsPerPage, int totalItems, Collection<T> results) {
        this.order = order;
        this.category = category;
        this.query = query;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.results = results;
        this.totalPages = (int) Math.ceil((float) totalItems / itemsPerPage);
        this.isFirst = page == 0;
        this.isLast = itemsPerPage * page + results.size() > itemsPerPage * (totalPages - 1) && itemsPerPage * page + results.size() <= itemsPerPage * (totalPages);
    }

    public PaginatedSearchResult(String order, String category, String query, String state, String city, Collection<City> cities, int page, int itemsPerPage, int totalItems, Collection<T> results) {
        this.order = order;
        this.category = category;
        this.query = query;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
        this.results = results;
        this.totalPages = (int) Math.ceil((float) totalItems / itemsPerPage);
        this.isFirst = page == 0;
        this.isLast = itemsPerPage * page + results.size() > itemsPerPage * (totalPages - 1) && itemsPerPage * page + results.size() <= itemsPerPage * (totalPages);
        this.state = state;
        this.city = city;
        this.cities = cities;
    }

    public Collection<City> getCities() {
        return cities;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public void setResults(Collection<T> results) {
        this.results = results;
    }
}