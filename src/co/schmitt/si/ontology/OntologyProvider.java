package co.schmitt.si.ontology;

import co.schmitt.si.ontology.model.Location;
import co.schmitt.si.ontology.model.Sport;
import co.schmitt.si.ontology.model.SportCategory;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

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
    //    private static final String ONTOLOGY_FILE_ALT = "htw_rdfs.owl";
    private static final String IRI_SEPARATOR = "#";

    private static final String SPORT_SPORTS = "Sportart";
    private static final String SPORT_LOCATIONS = "Ort";
    private static final String SPORT_CATEGORIES = "Sportkategorie";
    private static final String INDIVIDUAL_SPORTS = "Einzelsportarten";
    private static final String TEAM_SPORTS = "Mannschaftssportarten";
    /*private static final String INDOOR_SPORTS = "SportartenDrinnen";
    private static final String OUTDOOR_SPORTS = "SportartenDraussen";
    private static final String WATER_SPORTS = "SportartenWasser";
    private static final String BOAT_SPORTS = "SportartenBoot";
    private static final String ON_WATER_SPORTS = "SportartenAufDemWasser";
    private static final String IN_WATER_SPORTS = "SportartenImWasser";*/

    // Sport categories
   /* private static final String CATEGORY_ARTISTRY = "SportartenArtistik";
    private static final String CATEGORY_ATHLETICS = "SportartenLeichtathletik";
    private static final String CATEGORY_BALL_SPORTS = "SportartenBallSport";
    private static final String CATEGORY_FENCING = "SportartenFechtsport";
    private static final String CATEGORY_FITNESS = "SportartenFitness";
    private static final String CATEGORY_MARTIAL_ARTS = "SportartenKampfsport";
    private static final String CATEGORY_BOWLS = "SportartenKugelsport";
    private static final String CATEGORY_MISC = "SportartenMiscellaneous";
    private static final String CATEGORY_CYCLING = "SportartenRadsport";
    private static final String CATEGORY_DANCING = "SportartenTanzen";
    private static final String CATEGORY_WATER = "SportartenWassersport";
    private static final String CATEGORY_RACKET = "SportartenSchlaegersport";*/

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
     * @param className       The name of the class whose subclasses we are looking for
     * @param directSublasses Whether only direct subclasses should be returned
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
                OWLClass entity = ((OWLClass) sportEntity.iterator().next());
                if (!entity.isOWLNothing()) { // Return an empty list if no subclasses are found
                    subclasses.add(entity.getIRI().getFragment());
                }
            }
        }
        return subclasses;
    }

    /**
     * Execute a DL-Query and retrieve it result (subclasses)
     *
     * @param query The query
     * @return All sublasses returned by the query
     */
    private List<String> dlQuery(String query, boolean directSublasses) {
        List<String> subclasses = new ArrayList<String>();
        ShortFormProvider shortFormProvider = new SimpleShortFormProvider();   // Do we really need that ?
        DLQueryParser parser = new DLQueryParser(mOntology, shortFormProvider);
        OWLClassExpression classExpression = null;
        try {
            classExpression = parser.parseClassExpression(query);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        NodeSet<OWLClass> subClasses = mReasoner.getSubClasses(classExpression, directSublasses);
        for (Node sport : subClasses.getNodes()) {
            Set sportEntity = sport.getEntities();
            if (!sportEntity.isEmpty()) {
                OWLClass entity = ((OWLClass) sportEntity.iterator().next());
                if (!entity.isOWLNothing()) { // Return an empty list if no subclasses are found
                    subclasses.add(entity.getIRI().getFragment());
                }
            }
        }
        return subclasses;
    }

    /**
     * Retrieve a list of subclasses of a DL-Query
     * For testing purpose.
     *
     * @param query The query
     * @return A list containing all sports returned by the query
     */
    public List<Sport> SportsByDlQuery(String query) {
        return castToSport(dlQuery(query,true));
    }

    /**
     * Get all sports
     *
     * @return A list containing all sports
     */
    public List<Sport> getAllSports() {
        return castToSport(getAllSubclasses(SPORT_SPORTS, false));
    }

    /**
     * Get all locations
     *
     * @return A list containing all locations
     */
    public List<Location> getAllLocations() {
        return castToLocation(getAllSubclasses(SPORT_LOCATIONS, false));
    }

    /**
     * Get all sport categories
     *
     * @return A list containing all sport category
     */
    public List<SportCategory> getAllSportCategories() {
        return castToSportCategory(getAllSubclasses(SPORT_CATEGORIES, false));
    }

    /**
     * Get all sports from a category
     *
     * @param category The category
     * @return A list containing all sport within the category
     */
    public List<Sport> getAllSportsByCategory(SportCategory category) {
        return castToSport(dlQuery(DLQueries.SPORTS_BY_CATEGORY + category.getName(), true));
    }

    /**
     * Get all sports from a category
     *
     * @param location The category
     * @return A list containing all sport within the category
     */
    public List<Sport> getAllSportsByLocation(Location location) {
        return castToSport(dlQuery(DLQueries.SPORTS_BY_LOCATION + location.getName(), true));
    }

    /**
     * Get all sports from a category
     *
     * @param category The category
     * @param location The location
     * @return A list containing all sports within the category and the location
     */
    public List<Sport> getAllSportsByCategoryAndLocation(SportCategory category, Location location) {
        return castToSport(dlQuery(DLQueries.SPORTS_BY_CATEGORY + category.getName() + " and " + DLQueries.SPORTS_BY_LOCATION + location.getName(), true));
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
        return castToSport(dlQuery(DLQueries.SPORTS_INDOOR, true));
    }

    /**
     * Get all outdoor sports
     *
     * @return A list containing all outdoor sport
     */
    public List<Sport> getAllOutdoorSports() {
        return castToSport(dlQuery(DLQueries.SPORTS_OUTDOOR, true));
    }

    /**
     * Get all water sports
     *
     * @return A list containing all water sport
     */
    public List<Sport> getAllWaterSports() {
        return castToSport(dlQuery(DLQueries.SPORTS_WATER, true));
    }

    /**
     * Get all water sports (ImWasser)
     *
     * @return A list containing all water sport  (ImWasser)
     */
    public List<Sport> getAllInWaterSports() {
        return castToSport(dlQuery(DLQueries.SPORTS_IN_WATER, true));
    }

    /**
     * Get all water sports (AufDemWasser)
     *
     * @return A list containing all water sport  (AufDemWasser)
     */
    public List<Sport> getAllOnWaterSports() {
        return castToSport(dlQuery(DLQueries.SPORTS_ON_WATER, true));
    }

    /**
     * Get all boat sports
     *
     * @return A list containing all boat sport
     */
    public List<Sport> getAllBoatSports() {
        return castToSport(dlQuery(DLQueries.SPORTS_BOAT, true));
    }

    /**
     * Transform a string list to a sports list
     *
     * @param list The string list
     * @return A sports list
     */
    private List<Sport> castToSport(List<String> list) {
        List<Sport> sportsList = new ArrayList<Sport>();
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
        List<SportCategory> sportCategoriesList = new ArrayList<SportCategory>();
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
        List<Location> locationList = new ArrayList<Location>();
        for (String name : list) {
            locationList.add(new Location(name));
        }
        return locationList;
    }
}
