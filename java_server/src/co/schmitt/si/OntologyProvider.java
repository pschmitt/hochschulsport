package co.schmitt.si;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private HashMap<String, IRI> mClasses;
    private Reasoner mReasoner;

    // Constants
    private static final String ONTOLOGY_FILE = "htw_sport_entwurf_008.owl";
    private static final String ONTOLOGY_FILE_ALT = "htw_rdfs.owl";
    private static final String IRI_SEPARATOR = "#";

    /**
     * Constructor, sets up our object (private, cuz that's a singleton, bro !)
     */
    private OntologyProvider() {
        mClasses = new HashMap<String, IRI>();
        // BAUSTELLE

        File owlFile = null;
        try {
            owlFile = new File(ClassLoader.getSystemClassLoader().getResource(ONTOLOGY_FILE).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mOwlManager = OWLManager.createOWLOntologyManager();
        mOwlDataFactory = mOwlManager.getOWLDataFactory();
        try {
            mOntology = mOwlManager.loadOntologyFromOntologyDocument(owlFile);
            mPrefix = mOntology.getOntologyID().getOntologyIRI() + IRI_SEPARATOR;
            mReasoner = new Reasoner(mOntology);
            // Setup HashMap ([className] -> classURI)
            Set<OWLClass> allClasses = mOntology.getClassesInSignature();
            for (OWLClass cls : allClasses) {
                Set<OWLAnnotation> ann = cls.getAnnotations(mOntology);
                for (OWLAnnotation a : ann) {
                    mClasses.put(cleanClassName(a.getValue().toString()), cls.getIRI());
                }
            }
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
        result = checkHashMap();
        if (!result)
            System.err.println("There was something wrong when setting up the hashmap");
        return result;
    }

    /**
     * Check if the HashMap holding all IRIs and classnames was correctly set up
     *
     * @return true, if everything is okay
     */
    private boolean checkHashMap() {
        return (mClasses != null) && (mClasses.size() > 0);
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
     *
     * TODO Do not return sports categories (ontology has be changed)
     * TODO Some sports seem to be missing (those how have an empty classname)
     *
     * @param className The name of the class whose subclasses we are looking for
     * @return All sublasses of className
     */
    private List<String> getAllSubclasses(String className) {
        List<String> subclasses = new ArrayList<String>();
        IRI iri = getIRI(className);
        OWLClass teamSport = mOwlDataFactory.getOWLClass(iri);
//        Set<OWLClassExpression> allTeamSports = teamSport.getSubClasses(mOntology);
        // BAUSTELLE
        NodeSet subs = mReasoner.getSubClasses(teamSport, false);
        for (Node sport : (Set<Node>) subs.getNodes()) {
            String iriString = extractUrl(sport.toString());
            if (iriString != null) {
                String subClassName = getClassName(IRI.create(iriString));
                if (subClassName != null) {
                    subclasses.add(subClassName);
                    System.out.println("node: " + subClassName);
                }
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
        return getAllSubclasses("Mannschaftssport");
    }

    /**
     * Get all inidividual sports
     *
     * @return A list containing all individual sport names
     */
    public List<String> getAllIndividualSports() {
        return getAllSubclasses("Einzelsport");
    }

    /**
     * Retrieve a class specific IRI
     *
     * @param className Name of the class
     * @return The IRI
     */

    private IRI getIRI(String className) {
        return mClasses.get(className);
    }

    /**
     * Retrieve the class name from its IRI
     *
     * @param iri The IRI of the class
     * @return The name of the class
     */
    private String getClassName(IRI iri) {
        if (!mClasses.containsValue(iri))
            return null;
        for (Map.Entry<String, IRI> entry : mClasses.entrySet()) {
            if (entry.getValue().equals(iri)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Strip language suffix and unnecessary quotes
     *
     * @param className Name of the class (dirty)
     * @return Name of the class (clean)
     */
    private String cleanClassName(String className) {
        return className.replaceAll("@en", "").replaceAll("\"", "");
    }

    /**
     * Extract an URL from a String
     * Source: http://stackoverflow.com/a/1806161/1872036
     *
     * @param input The String to parse
     * @return The URI, or null if input does not contain any URL
     */
    public static String extractUrl(String input) {
        Pattern pattern = Pattern.compile(
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                        "|mil|biz|info|mobi|name|aero|jobs|museum" +
                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * Prints out the properties that instances of a class expression must have
     *
     * @param man      The manager
     * @param ont      The ontology
     * @param reasoner The reasoner
     * @param cls      The class expression
     */
    private static void printProperties(OWLOntologyManager man, OWLOntology ont, OWLReasoner reasoner, OWLClass cls) {
        if (!ont.containsClassInSignature(cls.getIRI())) {
            throw new RuntimeException("Class not in signature of the ontology");
        }
        // Note that the following code could be optimised... if we find that
        // instances of the specified class do not have a property, then we
        // don't need to check the sub properties of this property
        System.out.println("Properties of " + cls);
        for (OWLObjectPropertyExpression prop : ont.getObjectPropertiesInSignature()) {
            boolean sat = hasProperty(man, reasoner, cls, prop);
            if (sat) {
                System.out.println("Instances of " + cls + " necessarily have the property " + prop);
            }
        }
    }

    /**
     * Check whether a given object has a given property
     *
     * @param man      The manager
     * @param reasoner The reasoner
     * @param cls      The class expression
     * @param prop     The property
     * @return true, if the property exists
     */
    private static boolean hasProperty(OWLOntologyManager man, OWLReasoner reasoner, OWLClass cls, OWLObjectPropertyExpression prop) {
        // To test whether the instances of a class must have a property we
        // create a some values from restriction and then ask for the
        // satisfiability of the class interesected with the complement of this
        // some values from restriction. If the intersection is satisfiable then
        // the instances of the class don't have to have the property,
        // otherwise, they do.
        OWLDataFactory dataFactory = man.getOWLDataFactory();
        OWLClassExpression restriction = dataFactory.getOWLObjectSomeValuesFrom(prop, dataFactory.getOWLThing());
        // Now we see if the intersection of the class and the complement of
        // this restriction is satisfiable
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(restriction);
        OWLClassExpression intersection = dataFactory.getOWLObjectIntersectionOf(cls, complement);
        return !reasoner.isSatisfiable(intersection);
    }
}
