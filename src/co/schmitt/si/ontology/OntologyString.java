package co.schmitt.si.ontology;

/**
 * User: pschmitt
 * Date: 8/6/13
 * Time: 4:29 PM
 */
public class OntologyString {
    private OntologyString() {}

    public static String convert(String className) {
        return className.replaceAll("_", " ");
    }
}
