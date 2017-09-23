package ir.ac.iust.dml.kg.knowledge.store.export;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.core.TypedValue;
import ir.ac.iust.dml.kg.knowledge.core.ValueType;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Ontology;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import virtuoso.rdf4j.driver.VirtuosoRepository;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "Duplicates"})
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource({"classpath:persistence-context2.xml"})
public class Application implements CommandLineRunner {
    @Autowired
    private ir.ac.iust.dml.kg.knowledge.store.access2.dao.IOntologyDao ontologyDao2;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Override
    public void run(String... strings) throws Exception {
        final String ip = "localhost";
        final String port = "1111";
        final String user = "dba";
        final String password = "dba";
        VirtuosoRepository repository = new VirtuosoRepository("jdbc:virtuoso://" + ip + ":" + port + "/",
                user, password);
        try (RepositoryConnection con = repository.getConnection()) {
            con.clear(SimpleValueFactory.getInstance().createIRI("http://test.com"));
            exportOntology(con, 5, 20);

        }
    }

    private void exportOntology(RepositoryConnection con, float minProgress, float maxProgress) {
        printProgress(0, minProgress, maxProgress);
        PagingList<Ontology> result = null;
        do {
            result = ontologyDao2.search(null, null, null, null, null,
                    result == null ? 0 : result.getPage() + 1, 1000);
            if (!result.getData().isEmpty()) {
                final ModelBuilder builder = new ModelBuilder();
                for (Ontology o : result.getData())
                    if (hasValidURIs(o))
                        builder.namedGraph("http://test.com").add(o.getSubject(), o.getPredicate(), createValue(o.getObject()));
                final Model model = builder.build();
                for (Statement st : model)
                    con.add(st);
            }
            printProgress((float) (result.getPage()) / result.getPageCount(), minProgress, maxProgress);
        } while (result.getPage() < result.getPageCount());
        printProgress(1, minProgress, maxProgress);
    }


    private void printProgress(float val, float minProgress, float maxProgress) {
        System.out.println("#progress " + (minProgress + val * (maxProgress - minProgress)));
    }


    private boolean hasValidURIs(Ontology ontology) {
        try {
            new URL(ontology.getSubject());
            new URL(ontology.getPredicate());
            if (ontology.getObject().getType() == ValueType.Resource)
                new URL(ontology.getObject().getValue());
            return true;
        } catch (MalformedURLException e) {
            System.err.println("Has not valid format" + ontology);
            return false;
        }
    }

    private Object createValue(TypedValue v) {
        final ValueFactory vf = SimpleValueFactory.getInstance();
        if (v.getType() != null)
            switch (v.getType()) {
                case Resource:
                    return vf.createIRI(v.getValue());
                case String:
                    return vf.createLiteral(v.getValue(), v.getLang());
                case Boolean:
                    return vf.createLiteral(v.getValue(), XMLSchema.BOOLEAN);
                case Byte:
                    return vf.createLiteral(v.getValue(), XMLSchema.BYTE);
                case Short:
                    return vf.createLiteral(v.getValue(), XMLSchema.SHORT);
                case Integer:
                    return vf.createLiteral(v.getValue(), XMLSchema.INTEGER);
                case Long:
                    return vf.createLiteral(v.getValue(), XMLSchema.LONG);
                case Double:
                    return vf.createLiteral(v.getValue(), XMLSchema.DOUBLE);
                case Float:
                    return vf.createLiteral(v.getValue(), XMLSchema.FLOAT);

            }
        return vf.createLiteral(v.getValue());
    }
}
