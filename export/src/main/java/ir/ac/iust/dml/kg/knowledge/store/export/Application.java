package ir.ac.iust.dml.kg.knowledge.store.export;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.core.TypedValue;
import ir.ac.iust.dml.kg.knowledge.core.ValueType;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IOntologyDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.ISubjectDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IVersionDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Ontology;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Subject;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.TripleObject;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Version;
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
import virtuoso.rdf4j.driver.VirtuosoRepositoryConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 * <p>
 * Export data from mongo to virtuoso
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "Duplicates"})
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource({"classpath:persistence-context2.xml"})
public class Application implements CommandLineRunner {
    @Autowired
    private IOntologyDao ontologyDao;
    @Autowired
    private ISubjectDao subjectDao;
    @Autowired
    private IVersionDao versionDao;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Override
    public void run(String... strings) throws Exception {
        final String ip = "localhost";
        final String port = "1111";
        final String user = "dba";
        final String password = "fkgVIRTUOSO2017";
        final String tempGraph = "http://temp.fkg.iust.ir";
        final String finalGraph = "http://type2.fkg.iust.ir/";
        VirtuosoRepository repository = new VirtuosoRepository("jdbc:virtuoso://" + ip + ":" + port + "/",
                user, password);
        try (RepositoryConnection con = repository.getConnection()) {
            con.clear(SimpleValueFactory.getInstance().createIRI(tempGraph));
            exportOntology(con, 0, 10, tempGraph);
            exportTriples(con, 10, 100, tempGraph);
            con.clear(SimpleValueFactory.getInstance().createIRI(finalGraph));
            ((VirtuosoRepositoryConnection) con).executeSPARUL(String.format("MOVE <%s> TO <%s>", tempGraph, finalGraph));
            con.clear(SimpleValueFactory.getInstance().createIRI(tempGraph));

        }
    }

    private void exportTriples(RepositoryConnection con, float minProgress, float maxProgress, String tempGraph) {
        Map<String, Version> versionMap = new HashMap<>();
        versionDao.readAll().forEach(i -> versionMap.put(i.getModule(), i));

        printProgress(0, minProgress, maxProgress);
        PagingList<Subject> result = null;
        do {
            result = subjectDao.readAll(result == null ? 0 : result.getPage() + 1, 100);
            if (!result.getData().isEmpty()) {
                final ModelBuilder builder = new ModelBuilder();
                for (Subject s : result.getData()) {
                    int relationIndex = 0;
                    for (String p : s.getTriples().keySet()) {
                        final ArrayList<TripleObject> allObjects = s.getTriples().get(p);
                        final List<TypedValue> acceptedValues = new ArrayList<>();
                        final Map<String, Map<String, TypedValue>> acceptedProperties = new HashMap<>();
                        for (TripleObject o : allObjects) {
                            if (o.getSource() == null || o.getSource().getModule() == null) continue;
                            final Version activeVersion = versionMap.get(o.getSource().getModule());
                            if (activeVersion == null || activeVersion.getActiveVersion() == null
                                    || o.getSource().getVersion() != null &&
                                    o.getSource().getVersion() >= activeVersion.getActiveVersion()) {
                                final String key = o.toString();
                                if (acceptedProperties.containsKey(key)) {
                                    acceptedProperties.get(key).putAll(o.getProperties());
                                } else {
                                    acceptedValues.add(o);
                                    acceptedProperties.put(key, o.getProperties());
                                }
                            }
                        }
                        for (TypedValue o : acceptedValues) {
                            final String key = o.toString();
                            final Map<String, TypedValue> properties = acceptedProperties.get(key);
                            if (properties.isEmpty()) {
                                if (hasValidURIs(s.getSubject(), p, o))
                                    builder.namedGraph(tempGraph).add(s.getSubject(), p, createValue(o));
                            } else {
                                final String relation = s.getSubject() + "/relation_" + relationIndex++;
                                final TypedValue relationValue = new TypedValue(ValueType.Resource, relation);
                                if (hasValidURIs(s.getSubject(), p, relationValue)) {
                                    builder.namedGraph(tempGraph)
                                            .add(s.getSubject(), "http://fkg.iust.ac.ir/ontology/relatedPredicates", createValue(relationValue))
                                            .add(relation, "https://www.w3.org/1999/02/22-rdf-syntax-ns#type", SimpleValueFactory.getInstance().createIRI("http://fkg.iust.ac.ir/ontology/RelatedPredicates"))
                                            .add(relation, "http://fkg.iust.ac.ir/ontology/mainPredicate", SimpleValueFactory.getInstance().createIRI(p))
                                            .add(relation, p, createValue(o));
                                    for (Map.Entry<String, TypedValue> prop : properties.entrySet()) {
                                        if (hasValidURIs(relation, prop.getKey(), prop.getValue()))
                                            builder.namedGraph(tempGraph).add(relation, prop.getKey(),
                                                    createValue(prop.getValue()));
                                    }
                                }
                            }
                        }
                    }
                }
                final Model model = builder.build();
                for (Statement st : model)
                    con.add(st);
            }
            printProgress((float) (result.getPage()) / result.getPageCount(), minProgress, maxProgress);
        } while (result.getPage() < result.getPageCount());
        printProgress(1, minProgress, maxProgress);
    }

    private void exportOntology(RepositoryConnection con, float minProgress, float maxProgress, String tempGraph) {
        printProgress(0, minProgress, maxProgress);
        PagingList<Ontology> result = null;
        do {
            result = ontologyDao.search(null, null, null, null, null,
                    result == null ? 0 : result.getPage() + 1, 1000);
            if (!result.getData().isEmpty()) {
                final ModelBuilder builder = new ModelBuilder();
                for (Ontology o : result.getData())
                    if (hasValidURIs(o))
                        builder.namedGraph(tempGraph).add(o.getSubject(), o.getPredicate(), createValue(o.getObject()));
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

    private boolean hasValidURIs(String subject, String predicate, TypedValue object) {
        try {
            new URL(subject);
            new URL(predicate);
            if (object.getType() == ValueType.Resource)
                new URL(object.getValue());
            return true;
        } catch (MalformedURLException e) {
            System.err.printf("Has not valid format <%s %s %s> %n", subject, predicate, object);
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
