package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.JobCategories;

import java.util.Collection;

public interface JobCategoriesDao {
    Collection<JobCategories> getJobs();
}
