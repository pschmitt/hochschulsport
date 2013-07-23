import co.schmitt.si.OntologyProvider;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.util.ArrayList;

public class Server {
    private static final int port = 8080;

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer(port);

        XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

        PropertyHandlerMapping phm = new PropertyHandlerMapping();

        // provide the handler the class Adviser
        phm.addHandler("Adviser", Adviser.class);
        xmlRpcServer.setHandlerMapping(phm);

        XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);

        webServer.start();
        System.out.println("Server Running on Port: " + port);
        testOntologyProvider();
    }

    private static void testOntologyProvider() {
        OntologyProvider provider = OntologyProvider.getInstance();
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