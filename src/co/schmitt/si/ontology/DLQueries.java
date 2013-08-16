package co.schmitt.si.ontology;

/**
 * Created by pschmitt on 7/25/13.
 */
public final class DLQueries {
    private DLQueries() {}

    protected static final String TEAM_OR_INDIVIDUAL_SPORT = "istMannschaftssport exactly 1 ";
    protected static final String SPORTS_BY_CATEGORY = "gehoertZuOberkategorie exactly 1 ";
    protected static final String SPORTS_BY_LOCATION = "hatOrt some ";
    protected static final String ALL_SPORTS = "Sportart ";
    protected static final String SPORTS_INDOOR = "hatOrt exactly 1 Drinnen";
    protected static final String SPORTS_OUTDOOR = "hatOrt exactly 1 Draussen";
    protected static final String SPORTS_WATER = "hatOrt exactly 1 Wasser";
    protected static final String SPORTS_IN_WATER = "hatOrt exactly 1 ImWasser";
    protected static final String SPORTS_ON_WATER = "hatOrt exactly 1 AufDemWasser";
    protected static final String SPORTS_BOAT = "hatOrt exactly 1 Boot";
}
