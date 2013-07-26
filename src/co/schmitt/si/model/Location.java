package co.schmitt.si.model;

/**
 * Created by pschmitt on 7/25/13.
 */
public class Location {
    public enum LOCATION_TYPE {
        INDOOR, OUTDOOR, WATER, ON_WATER, IN_WATER, BOAT
    }

    private String name;
    private LOCATION_TYPE type;

    public Location(LOCATION_TYPE type) {
        this.type = type;
    }

    public Location(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(LOCATION_TYPE type) {
        this.type = type;
    }

    public LOCATION_TYPE getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "<Location> " + name + " (type: " + type + ")";
    }
}
