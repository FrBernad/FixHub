package ar.edu.itba.paw.models;

public class UserStats {
    private long jobsCount;
    private long avgRating;
    private long reviewCount;

    public UserStats(long jobsCount, long avgRating, long reviewCount) {
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
        if (o == null || getClass() != o.getClass()) return false;
        UserStats userStats = (UserStats) o;
        return jobsCount == userStats.jobsCount && avgRating == userStats.avgRating && reviewCount == userStats.reviewCount;
    }

}
