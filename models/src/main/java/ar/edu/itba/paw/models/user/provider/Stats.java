package ar.edu.itba.paw.models.user.provider;

import java.util.Objects;

public class Stats {
    private long jobsCount;
    private long avgRating;
    private long reviewCount;

    public Stats(long jobsCount, long avgRating, long reviewCount) {
        this.jobsCount = jobsCount;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }

    public long getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(long jobsCount) {
        this.jobsCount = jobsCount;
    }

    public long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(long avgRating) {
        this.avgRating = avgRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stats)) return false;
        Stats stats = (Stats) o;
        return jobsCount == stats.jobsCount && avgRating == stats.avgRating && reviewCount == stats.reviewCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobsCount, avgRating, reviewCount);
    }

    @Override
    public String toString() {
        return "Stats{" +
            "jobsCount=" + jobsCount +
            ", avgRating=" + avgRating +
            ", reviewCount=" + reviewCount +
            '}';
    }
}
