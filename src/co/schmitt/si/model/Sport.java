package co.schmitt.si.model;

/**
 * Created by pschmitt on 7/25/13.
 */
public class Sport {
    private int dbId = -1;
    private String name;

    public Sport(String name) {
        this.name = name;
    }

    public Sport(String name, int dbId) {
        this.name = name;
        this.dbId = dbId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getDbId() {
        return dbId;
    }

    @Override
    public String toString() {
        return "<Sport> " + name + " (dbID: " + dbId + ")";
    }
}
