package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.location.City;
import ar.edu.itba.paw.models.location.State;
import ar.edu.itba.paw.models.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "provider_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_location_pl_id_seq")
    @SequenceGenerator(sequenceName = "provider_location_pl_id_seq", name = "provider_location_pl_id_seq", allocationSize = 1)
    @Column(name = "pl_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pl_provider_id")
    private User provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pl_state_id")
    private State state;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "provider_cities",
        joinColumns = @JoinColumn(name = "pc_provider_id"),
        foreignKey = @ForeignKey(name = "pl_provider_id"),
        inverseJoinColumns = @JoinColumn(name = "pc_city_id")
    )
    private Set<City> cities;

    public Location(User provider, Set<City> cities, State state) {
        this.provider = provider;
        this.cities = cities;
        this.state = state;
    }

    /* default */
    protected Location() {
        // Just for Hibernate
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

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
