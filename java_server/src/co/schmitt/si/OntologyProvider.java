package co.schmitt.si;

import co.schmitt.si.model.Location;
import co.schmitt.si.model.Sport;
import co.schmitt.si.model.SportCategory;
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

    private static final String SPORT_LOCATIONS = "Ort";
    private static final String SPORT_CATEGORIES = "Sportkategorie";
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
        try {
            File owlFile = new File(ClassLoader.getSystemClassLoader().getResource(ONTOLOGY_FILE).toURI());
            mOwlManager = OWLManager.createOWLOntologyManager();
            mOwlDataFactory = mOwlManager.getOWLDataFactory();
            mOntology = mOwlManager.loadOntologyFromOntologyDocument(owlFile);
            mPrefix = mOntology.getOntologyID().getOntologyIRI() + IRI_SEPARATOR;
            mReasoner = new Reasoner(mOntology);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
    private List<String> getAllSubclasses(String className, boolean directSublasses) {
        List<String> subclasses = new ArrayList<String>();
        IRI iri = IRI.create(mPrefix + className);
        OWLClassExpression teamSport = mOwlDataFactory.getOWLClass(iri);
        NodeSet<OWLClass> allTeamSports = mReasoner.getSubClasses(teamSport, directSublasses); // true -> direct subclasses /!\ Nothing
        for (Node sport : allTeamSports.getNodes()) {
            Set sportEntity = sport.getEntities();
            if (!sportEntity.isEmpty()) {
                subclasses.add(((OWLClass) sportEntity.iterator().next()).getIRI().getFragment());
            }
        }
        return subclasses;
    }

    public List<Location> getAllLocations() {
        return castToLocation(getAllSubclasses(SPORT_LOCATIONS, true));
    }

    /**
     * Get all sport categories
     *
     * @return A list containing all sport category
     */
    public List<SportCategory> getAllSportCategories() {
        return castToSportCategory(getAllSubclasses(SPORT_CATEGORIES, true));
    }

    /**
     * Get all sports from a category
     *
     * @param category The category
     * @return A list containing all sport within the category
     */
    public List<Sport> getAllSports(SportCategory category) {
        return castToSport(getAllSubclasses(category.getName(), true));
    }

    /**
     * Get all sports from a category
     *
     * @param category The category
     * @param location The location
     * @return A list containing all sports within the category and the location
     */
    public List<Sport> getAllSports(SportCategory category, Location location) {
        // TODO !
        return null;
    }

    /**
     * Get all team sports
     *
     * @return A list containing all team sport 
     */
    public List<Sport> getAllTeamSports() {
        return castToSport(getAllSubclasses(TEAM_SPORTS, true));
    }

    /**
     * Get all inidividual sports
     *
     * @return A list containing all individual sport 
     */
    public List<Sport> getAllIndividualSports() {
        return castToSport(getAllSubclasses(INDIVIDUAL_SPORTS, true));
    }

    /**
     * Get all indoor sports
     *
     * @return A list containing all indoor sport 
     */
    public List<Sport> getAllIndoorSports() {
        return castToSport(getAllSubclasses(INDOOR_SPORTS, true));
    }

    /**
     * Get all outdoor sports
     *
     * @return A list containing all outdoor sport 
     */
    public List<Sport> getAllOutdoorSports() {
        return castToSport(getAllSubclasses(OUTDOOR_SPORTS, true));
    }

    /**
     * Get all water sports
     *
     * @return A list containing all water sport 
     */
    public List<Sport> getAllWaterSports() {
        return castToSport(getAllSubclasses(WATER_SPORTS, true));
    }

    /**
     * Get all water sports (ImWasser)
     *
     * @return A list containing all water sport  (ImWasser)
     */
    public List<Sport> getAllInWaterSports() {
        return castToSport(getAllSubclasses(IN_WATER_SPORTS, true));
    }

    /**
     * Get all water sports (AufDemWasser)
     *
     * @return A list containing all water sport  (AufDemWasser)
     */
    public List<Sport> getAllOnWaterSports() {
        return castToSport(getAllSubclasses(ON_WATER_SPORTS, true));
    }

    /**
     * Get all boat sports
     *
     * @return A list containing all boat sport 
     */
    public List<Sport> getAllBoatSports() {
        return castToSport(getAllSubclasses(BOAT_SPORTS, true));
    }

    /**
     * Transform a string list to a sports list
     *
     * @param list The string list
     * @return A sports list
     */
    private List<Sport> castToSport(List<String> list) {
        ArrayList<Sport> sportsList = new ArrayList<Sport>();
        for (String name : list) {
            sportsList.add(new Sport(name));
        }
        return sportsList;
    }

    /**
     * Transform a string list to a sport categories list
     *
     * @param list The string list
     * @return A sport categories list
     */
    private List<SportCategory> castToSportCategory(List<String> list) {
        ArrayList<SportCategory> sportCategoriesList = new ArrayList<SportCategory>();
        for (String name : list) {
            sportCategoriesList.add(new SportCategory(name));
        }
        return sportCategoriesList;
    }

    /**
     * Transform a string list to a location list
     *
     * @param list The string list
     * @return A location list
     */
    private List<Location> castToLocation(List<String> list) {
        ArrayList<Location> locationList = new ArrayList<Location>();
        for (String name : list) {
            locationList.add(new Location(name));
        }
        return locationList;
    }
}
