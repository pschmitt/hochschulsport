package co.schmitt.si.ontology;

/**
 * Created by pschmitt on 7/25/13.
 */
public class DLQueries {
    private DLQueries() {}

    public static final String SPORTS_BY_CATEGORY = "gehoertZuOberkategorie exactly 1 ";
    public static final String SPORTS_BY_LOCATION = "hatOrt exactly 1 ";
    public static final String ALL_SPORTS = "Sportart ";
    public static final String SPORTS_INDOOR = "hatOrt exactly 1 Drinnen";
    public static final String SPORTS_OUTDOOR = "hatOrt exactly 1 Draussen";
    public static final String SPORTS_WATER = "hatOrt exactly 1 Wasser";
    public static final String SPORTS_IN_WATER = "hatOrt exactly 1 ImWasser";
    public static final String SPORTS_ON_WATER = "hatOrt exactly 1 AufDemWasser";
    public static final String SPORTS_BOAT = "hatOrt exactly 1 Boot";
}
