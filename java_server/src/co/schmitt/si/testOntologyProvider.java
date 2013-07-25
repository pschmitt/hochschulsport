package co.schmitt.si;

import co.schmitt.si.model.Location;
import co.schmitt.si.model.Sport;
import co.schmitt.si.model.SportCategory;

import java.util.ArrayList;

/**
 * Created by pschmitt on 7/19/13.
 */
public class testOntologyProvider {
    private static OntologyProvider mProvider;

    public static void main(String[] args) {
        mProvider = OntologyProvider.getInstance();
        mProvider.check();

                testAllSports();
        //        testSportCategories();
        //        testLocations();
        //        testTeamSports();
        //        testIndividualSports();
//        testIndoorSports();
        //        testOutdoorSports();
        //        testWaterSports();
        //        testInWaterSports();
        //        testOnWaterSports();
        //        testBoatSports();
        //        testSportsByCategory(new SportCategory("Ballsport"));
        //        testSportsByLocation(new Location("Boot"));
        //        testSportsByCategoryAndLocation(new SportCategory("Ballsport"), new Location("Drinnen"));
        //        testDlQuery("gehoertZuOberkategorie exactly 1 Tanzen and hatOrt exactly 1 Drinnen");
    }

    private static void testAllSports() {
        ArrayList<Sport> sports = (ArrayList<Sport>) mProvider.getAllSports();
        for (Sport sport : sports) {
            System.out.println(sport + " is a sport !");
        }
        System.out.println("# sports: " + sports.size());
    }

    private static void testLocations() {
        ArrayList<Location> locations = (ArrayList<Location>) mProvider.getAllLocations();
        for (Location sport : locations) {
            System.out.println(sport + " is a location !");
        }
        System.out.println("# locations: " + locations.size());
    }

    private static void testSportsByLocation(Location location) {
        ArrayList<Sport> sportsByLocation = (ArrayList<Sport>) mProvider.getAllSportsByLocation(location);
        for (Sport sport : sportsByLocation) {
            System.out.println(sport + " is a " + location.getName() + " !");
        }
        System.out.println("# sports in " + location.getName() + ": " + sportsByLocation.size());
    }

    private static void testSportCategories() {
        ArrayList<SportCategory> categories = (ArrayList<SportCategory>) mProvider.getAllSportCategories();
        for (SportCategory sport : categories) {
            System.out.println(sport + " is a sport category !");
        }
        System.out.println("# sport categories: " + categories.size());
    }

    private static void testSportsByCategory(SportCategory category) {
        ArrayList<Sport> sportsByCategory = (ArrayList<Sport>) mProvider.getAllSportsByCategory(category);
        for (Sport sport : sportsByCategory) {
            System.out.println(sport + " is a " + category.getName() + " !");
        }
        System.out.println("# sports in " + category.getName() + ": " + sportsByCategory.size());
    }

    private static void testSportsByCategoryAndLocation(SportCategory category, Location location) {
        ArrayList<Sport> teamSports = (ArrayList<Sport>) mProvider.getAllSportsByCategoryAndLocation(category, location);
        for (Sport sport : teamSports) {
            System.out.println(sport + " is a " + category.getName() + " + " + location.getName() + " !");
        }
        System.out.println("# sports in " + category.getName() + " + " + location.getName() + ": " + teamSports.size());
    }

    private static void testTeamSports() {
        ArrayList<Sport> teamSports = (ArrayList<Sport>) mProvider.getAllTeamSports();
        for (Sport sport : teamSports) {
            System.out.println(sport + " is a team sport !");
        }
        System.out.println("# team sports: " + teamSports.size());
    }

    private static void testIndividualSports() {
        ArrayList<Sport> indivSports = (ArrayList<Sport>) mProvider.getAllIndividualSports();
        for (Sport sport : indivSports) {
            System.out.println(sport + " is an invidual sport !");
        }
        System.out.println("# indiv sports: " + indivSports.size());
    }

    private static void testOutdoorSports() {
        ArrayList<Sport> outdoorSports = (ArrayList<Sport>) mProvider.getAllOutdoorSports();
        for (Sport sport : outdoorSports) {
            System.out.println(sport + " is an outdoor sport !");
        }
        System.out.println("# outdoor sports: " + outdoorSports.size());
    }

    private static void testIndoorSports() {
        ArrayList<Sport> indoorSports = (ArrayList<Sport>) mProvider.getAllIndoorSports();
        for (Sport sport : indoorSports) {
            System.out.println(sport + " is an indoor sport !");
        }
        System.out.println("# indoor sports: " + indoorSports.size());
    }

    private static void testWaterSports() {
        ArrayList<Sport> waterSports = (ArrayList<Sport>) mProvider.getAllWaterSports();
        for (Sport sport : waterSports) {
            System.out.println(sport + " is a water sport !");
        }
        System.out.println("# water sports: " + waterSports.size());
    }

    private static void testInWaterSports() {
        ArrayList<Sport> inWaterSports = (ArrayList<Sport>) mProvider.getAllInWaterSports();
        for (Sport sport : inWaterSports) {
            System.out.println(sport + " is a (in) water sport !");
        }
        System.out.println("# (in) water sports: " + inWaterSports.size());
    }

    private static void testOnWaterSports() {
        ArrayList<Sport> inWaterSports = (ArrayList<Sport>) mProvider.getAllOnWaterSports();
        for (Sport sport : inWaterSports) {
            System.out.println(sport + " is a (on) water sport !");
        }
        System.out.println("# (on) water sports: " + inWaterSports.size());
    }

    private static void testBoatSports() {
        ArrayList<Sport> boatSports = (ArrayList<Sport>) mProvider.getAllBoatSports();
        for (Sport sport : boatSports) {
            System.out.println(sport + " is a boat sport !");
        }
        System.out.println("# boat sports: " + boatSports.size());
    }

    private static void testDlQuery(String query) {
        ArrayList<Sport> queryResults = (ArrayList<Sport>) mProvider.SportsByDlQuery(query);
        for (Sport sport : queryResults) {
            System.out.println(sport + " is a subclass of " + query + " !");
        }
        System.out.println("# results: " + queryResults.size());
    }
}