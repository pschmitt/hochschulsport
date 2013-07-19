import java.util.ArrayList;

/**
 * Created by pschmitt on 7/19/13.
 */
public class main {
    public static void main(String[] args) {
        OntologyProvider provider = new OntologyProvider();
        provider.check();
        ArrayList<String> teamSports = (ArrayList<String>) provider.getAllTeamSports();
        for (String sport : teamSports) {
            System.out.println(sport + " is a team sport !");
        }
        ArrayList<String> indivSports = (ArrayList<String>) provider.getAllIndividualSports();
        for (String sport : indivSports) {
            System.out.println(sport + " is an invidual sport !");
        }

        System.out.println("# team sports: " + teamSports.size());
        System.out.println("# indiv sports: " + indivSports.size());
        int total = teamSports.size() + indivSports.size();
        System.out.println("Total: " + total);
    }
}