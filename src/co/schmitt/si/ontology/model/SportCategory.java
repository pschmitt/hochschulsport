package co.schmitt.si.ontology.model;

/**
 * Created by pschmitt on 7/25/13.
 */
public class SportCategory {
    private String name;

    public SportCategory(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "<SportCategory> " + name;
    }
}
