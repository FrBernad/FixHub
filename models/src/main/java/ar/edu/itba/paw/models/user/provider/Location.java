package ar.edu.itba.paw.models.user.provider;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.State;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "provider_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_location_pl_id_seq")
    @SequenceGenerator(sequenceName = "provider_location_pl_id_seq", name = "provider_location_pl_id_seq", allocationSize = 1)
    @Column(name = "pl_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "pl_provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "pl_state_id")
    private State state;

    @ManyToMany
    @JoinTable(name = "provider_cities",
        joinColumns = @JoinColumn(name = "pc_provider_id"),
        foreignKey = @ForeignKey(name = "pl_provider_id")
    )
    private Collection<City> cities;

    public Location(Provider provider, Collection<City> cities, State state) {
        this.provider = provider;
        this.cities = cities;
        this.state = state;
    }

    /* default */
    protected Location() {
        // Just for Hibernate
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> cities) {
        this.cities = cities;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
