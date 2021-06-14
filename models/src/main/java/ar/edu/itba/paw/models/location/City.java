package ar.edu.itba.paw.models.location;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_c_id_seq")
    @SequenceGenerator(sequenceName = "cities_c_id_seq", name = "cities_c_id_seq", allocationSize = 1)
    @Column(name = "c_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_state_id")
    private State state;

    @Column(name = "c_name", nullable = false)
    private String name;

    /* default */
    protected City() {
        // Just for Hibernate
    }

    public City(State state, String name) {
        this.state = state;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
