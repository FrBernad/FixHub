package ar.edu.itba.paw.models.user.provider;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "provider_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sch_id_seq")
    @SequenceGenerator(sequenceName = "sch_id_seq", name = "sch_id_seq", allocationSize = 1)
    @Column(name = "sch_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sch_provider_id")
    private Provider provider;

    @Column(name = "sch_start_time", length = 5, nullable = false)
    private String startTime;

    @Column(name = "sch_end_time", length = 5, nullable = false)
    private String endTime;

     /* default */
    protected Schedule() {
        // Just for Hibernate
    }

    public Schedule(Provider provider, String startTime, String endTime) {
        this.provider = provider;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(provider, schedule.provider) && Objects.equals(startTime, schedule.startTime) && Objects.equals(endTime, schedule.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + id +
            ", provider=" + provider +
            ", startTime='" + startTime + '\'' +
            ", endTime='" + endTime + '\'' +
            '}';
    }
}
