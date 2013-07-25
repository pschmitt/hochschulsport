package co.schmitt.si;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: pschmitt
 * Date: 19/07/2013
 * Time: 21:36
 */
public class OntologyProvider {

    private static volatile OntologyProvider instance;

    private OWLOntologyManager mOwlManager;
    private OWLDataFactory mOwlDataFactory;
    private OWLOntology mOntology;
    private String mPrefix;
    private Reasoner mReasoner;

    // Constants
    private static final String ONTOLOGY_FILE = "htw_sport.owl";
    private static final String ONTOLOGY_FILE_ALT = "htw_rdfs.owl";
    private static final String IRI_SEPARATOR = "#";

    private static final String INDIVIDUAL_SPORTS = "Einzelsportarten";
    private static final String TEAM_SPORTS = "Mannschaftssportarten";
    private static final String INDOOR_SPORTS = "SportartenDrinnen";
    private static final String OUTDOOR_SPORTS = "SportartenDraussen";
    private static final String WATER_SPORTS = "SportartenWasser";
    private static final String BOAT_SPORTS = "SportartenBoot";
    private static final String ON_WATER_SPORTS = "SportartenAufDemWasser";
    private static final String IN_WATER_SPORTS = "SportartenImWasser";

    /**
     * Constructor, sets up our object (private, cuz that's a singleton, bro !)
     */
    private OntologyProvider() {
        File owlFile = null;
        try {
            owlFile = new File(ClassLoader.getSystemClassLoader().getResource(ONTOLOGY_FILE).toURI());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mOwlManager = OWLManager.createOWLOntologyManager();
        mOwlDataFactory = mOwlManager.getOWLDataFactory();
        try {
            mOntology = mOwlManager.loadOntologyFromOntologyDocument(owlFile);
            mPrefix = mOntology.getOntologyID().getOntologyIRI() + IRI_SEPARATOR;
            mReasoner = new Reasoner(mOntology);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    // Singleton
    public static OntologyProvider getInstance() {
        if (instance == null) {
            synchronized (OntologyProvider.class) {
                if (instance == null) {
                    instance = new OntologyProvider();
                }
            }
        }
        return instance;
    }

    /**
     * Do some dumb checks
     * DELETEME
     *
     * @return true, if the tests were passed
     */
    public boolean check() {
        boolean result = checkConsistency();
        if (!result)
            System.err.println("There is an error in the ontology... I may misbehave.");
        return result;
    }

    /**
     * Check the ontology for inconsistencies
     *
     * @return true, if there are no inconsistencies
     */
    private boolean checkConsistency() {
        return mReasoner.isConsistent();
    }

    /**
     * Helper class that returns all subclasses of a given class
     * <p/>
     * TODO Some sports seem to be missing (those how have an empty classname)
     *
     * @param className The name of the class whose subclasses we are looking for
     * @return All sublasses of className
     */
    private List<String> getAllSubclasses(String className) {
        List<String> subclasses = new ArrayList<String>();
        IRI iri = IRI.create(mPrefix + className);
        OWLClassExpression teamSport = mOwlDataFactory.getOWLClass(iri);
        NodeSet<OWLClass> allTeamSports = mReasoner.getSubClasses(teamSport, true); // true -> direct subclasses
        for (Node sport : allTeamSports.getNodes()) {
            Set sportEntity = sport.getEntities();
            if (!sportEntity.isEmpty()) {
                subclasses.add(((OWLClass) sportEntity.iterator().next()).getIRI().getFragment());
            }
        }
        return subclasses;
    }

    /**
     * Get all team sports
     *
     * @return A list containing all team sport names
     */
    public List<String> getAllTeamSports() {
        return getAllSubclasses(TEAM_SPORTS);
    }

    /**
     * Get all inidividual sports
     *
     * @return A list containing all individual sport names
     */
    public List<String> getAllIndividualSports() {
        return getAllSubclasses(INDIVIDUAL_SPORTS);
    }

    /**
     * Get all indoor sports
     *
     * @return A list containing all indoor sport names
     */
    public List<String> getAllIndoorSports() {
        return getAllSubclasses(INDOOR_SPORTS);
    }

    /**
     * Get all outdoor sports
     *
     * @return A list containing all outdoor sport names
     */
    public List<String> getAllOutdoorSports() {
        return getAllSubclasses(OUTDOOR_SPORTS);
    }

    /**
     * Get all water sports
     *
     * @return A list containing all water sport names
     */
    public List<String> getAllWaterSports() {
        return getAllSubclasses(WATER_SPORTS);
    }

    /**
     * Get all water sports (ImWasser)
     *
     * @return A list containing all water sport names (ImWasser)
     */
    public List<String> getAllInWaterSports() {
        return getAllSubclasses(IN_WATER_SPORTS);
    }

    /**
     * Get all water sports (AufDemWasser)
     *
     * @return A list containing all water sport names (AufDemWasser)
     */
    public List<String> getAllOnWaterSports() {
        return getAllSubclasses(ON_WATER_SPORTS);
    }

    /**
     * Get all boat sports
     *
     * @return A list containing all boat sport names
     */
    public List<String> getAllBoatSports() {
        return getAllSubclasses(BOAT_SPORTS);
    }
}
