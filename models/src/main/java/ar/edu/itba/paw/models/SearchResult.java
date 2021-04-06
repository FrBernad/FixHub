package ar.edu.itba.paw.models;

import java.util.Collection;

public class SearchResult {
    String order, filter, query;

    Collection<Job> jobs;

    public SearchResult(String order, String filter, String query, Collection<Job> jobs) {
        this.order = order;
        this.filter = filter;
        this.query = query;
        this.jobs = jobs;
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

    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }
}
