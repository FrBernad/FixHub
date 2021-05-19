package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.user.User;

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
    private User provider;

    @Column(name = "sch_start_time", length = 5, nullable = false)
    private String startTime;

    @Column(name = "sch_end_time", length = 5, nullable = false)
    private String endTime;

     /* default */
    protected Schedule() {
        // Just for Hibernate
    }

    public Schedule(User provider, String startTime, String endTime) {
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

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
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
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
