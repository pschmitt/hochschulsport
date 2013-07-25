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

        testSportCategories();
        testLocations();
        testTeamSports();
        testIndividualSports();
        testIndoorSports();
        testOutdoorSports();
        testWaterSports();
        testInWaterSports();
        testOnWaterSports();
        testBoatSports();
    }

    private static void testLocations() {
        ArrayList<Location> locations = (ArrayList<Location>) mProvider.getAllLocations();
        for (Location sport : locations) {
            System.out.println(sport + " is a sport category !");
        }
        System.out.println("# sport categories: " + locations.size());
    }

    private static void testSportCategories() {
        ArrayList<SportCategory> categories = (ArrayList<SportCategory>) mProvider.getAllSportCategories();
        for (SportCategory sport : categories) {
            System.out.println(sport + " is a sport category !");
        }
        System.out.println("# sport categories: " + categories.size());
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
}