package co.schmitt.si;

import java.util.ArrayList;

/**
 * Created by pschmitt on 7/19/13.
 */
public class testOntologyProvider {
    private static OntologyProvider mProvider;

    public static void main(String[] args) {
        mProvider = OntologyProvider.getInstance();
        mProvider.check();

        testTeamSports();
        testIndividualSports();
        testIndoorSports();
        testOutdoorSports();
        testWaterSports();
        testInWaterSports();
        testOnWaterSports();
        testBoatSports();
    }

    private static void testTeamSports() {
        ArrayList<String> teamSports = (ArrayList<String>) mProvider.getAllTeamSports();
        for (String sport : teamSports) {
            System.out.println(sport + " is a team sport !");
        }
        System.out.println("# team sports: " + teamSports.size());
    }

    private static void testIndividualSports() {
        ArrayList<String> indivSports = (ArrayList<String>) mProvider.getAllIndividualSports();
        for (String sport : indivSports) {
            System.out.println(sport + " is an invidual sport !");
        }
        System.out.println("# indiv sports: " + indivSports.size());
    }

    private static void testOutdoorSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllOutdoorSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is an outdoor sport !");
        }
        System.out.println("# outdoor sports: " + outdoorSports.size());
    }

    private static void testIndoorSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllIndoorSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is an indoor sport !");
        }
        System.out.println("# indoor sports: " + outdoorSports.size());
    }

    private static void testWaterSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllWaterSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is a water sport !");
        }
        System.out.println("# water sports: " + outdoorSports.size());
    }

    private static void testInWaterSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllInWaterSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is a (in) water sport !");
        }
        System.out.println("# (in) water sports: " + outdoorSports.size());
    }

    private static void testOnWaterSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllOnWaterSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is a (on) water sport !");
        }
        System.out.println("# (on) water sports: " + outdoorSports.size());
    }

    private static void testBoatSports() {
        ArrayList<String> outdoorSports = (ArrayList<String>) mProvider.getAllBoatSports();
        for (String sport : outdoorSports) {
            System.out.println(sport + " is a boat sport !");
        }
        System.out.println("# boat sports: " + outdoorSports.size());
    }
}