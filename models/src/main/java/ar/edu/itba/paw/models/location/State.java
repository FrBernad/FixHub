package ar.edu.itba.paw.models.location;

import javax.persistence.*;

@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "states_s_id_seq")
    @SequenceGenerator(sequenceName = "states_s_id_seq", name = "states_s_id_seq", allocationSize = 1)
    @Column(name = "s_id")
    private long id;

    @Column(name = "s_name", nullable = false)
    private String name;

    public State(String name) {
        this.name = name;
    }

    /* default */
    protected State() {
        // Just for Hibernate
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
