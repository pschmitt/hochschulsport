import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;

/**
 * Created by pschmitt on 7/19/13.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("OKAY");
        File owlFile = new File("htw_sport_entwurf_008.owl");
        OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology;
        try {
            ontology = owlManager.loadOntologyFromOntologyDocument(owlFile);
            Reasoner hermit = new Reasoner(ontology);
            System.out.println(hermit.isConsistent());
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}