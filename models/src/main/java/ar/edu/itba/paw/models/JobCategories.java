package ar.edu.itba.paw.models;

public class JobCategories {

    private Number id;
    private String name;

    public JobCategories(Number id, String name) {
        this.id = id;
        this.name = name;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JobCategories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
