package co.schmitt.si.ontology;

/**
 * Source: http://sourceforge.net/p/owlapi/code/ci/aef6981535f07a2d0d44c394b9f4d5415f36025a/tree/contract/src/test/java/org/coode/owlapi/examples/DLQueryExample.java#l210
 */

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.util.Set;

public class DLQueryParser {
    private OWLOntology rootOntology;
    private BidirectionalShortFormProvider bidiShortFormProvider;

    /**
     * Constructs a DLQueryParser using the specified ontology and short form
     * provider to map entity IRIs to short names.
     *
     * @param rootOntology      The root ontology. This essentially provides the domain
     *                          vocabulary for the query.
     * @param shortFormProvider A short form provider to be used for mapping back and forth
     *                          between entities and their short names (renderings).
     */
    public DLQueryParser(OWLOntology rootOntology, ShortFormProvider shortFormProvider) {
        this.rootOntology = rootOntology;
        OWLOntologyManager manager = rootOntology.getOWLOntologyManager();
        Set<OWLOntology> importsClosure = rootOntology.getImportsClosure();
        // Create a bidirectional short form provider to do the actual mapping.
        // It will generate names using the input
        // short form provider.
        bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(manager, importsClosure, shortFormProvider);
    }

    /**
     * Parses a class expression string to obtain a class expression.
     *
     * @param classExpressionString The class expression string
     * @return The corresponding class expression
     * @throws ParserException if the class expression string is malformed or contains
     *                         unknown entity names.
     */
    public OWLClassExpression parseClassExpression(String classExpressionString) throws ParserException {
        OWLDataFactory dataFactory = rootOntology.getOWLOntologyManager().getOWLDataFactory();
        // Set up the real co.schmitt.si.parser
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(dataFactory, classExpressionString);
        parser.setDefaultOntology(rootOntology);
        // Specify an entity checker that wil be used to check a class
        // expression contains the correct names.
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(bidiShortFormProvider);
        parser.setOWLEntityChecker(entityChecker);
        // Do the actual parsing
        return parser.parseClassExpression();
    }
}
