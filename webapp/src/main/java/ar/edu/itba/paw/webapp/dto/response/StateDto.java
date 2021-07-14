package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.location.State;

import java.util.Collection;
import java.util.stream.Collectors;

public class StateDto {

    public static Collection<StateDto> mapStateToDto(Collection<State> states) {
        return states.stream().map(StateDto::new).collect(Collectors.toList());
    }

    private long id;

    private String name;

    public StateDto() {
    }

    public StateDto(State state) {
        this.id = state.getId();
        this.name = state.getName();
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
