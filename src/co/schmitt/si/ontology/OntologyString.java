package co.schmitt.si.ontology;

/**
 * User: pschmitt
 * Date: 8/6/13
 * Time: 4:29 PM
 */
public class OntologyString {
    private OntologyString() {}

    public static String convert(String className) {
        //        return className.replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("Ä", "Ae").replaceAll("Ö", "Oe").replaceAll("Ü", "Ue").replaceAll("ß", "ss").replaceAll(" ", "_");
        return className.replaceAll("_", " ");
    }
}
