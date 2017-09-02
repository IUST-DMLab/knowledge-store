package ir.ac.iust.dml.kg.knowledge.store.access.test;

import ir.ac.iust.dml.kg.knowledge.core.TypedValue;
import ir.ac.iust.dml.kg.knowledge.core.ValueType;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IMappingDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IOntologyDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IVersionDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for access
 */
@SuppressWarnings("Duplicates")
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context2.xml")
public class AccessTest {
    @Autowired
    ITripleDao triples;
    @Autowired
    IMappingDao mappings;
    @Autowired
    IVersionDao versions;
    @Autowired
    IOntologyDao ontology;

    @Test
    public void testTripleDao() {
        Triple t1 = new Triple("context", "Hossein", "birth_year", "64");
        Triple t2 = new Triple("context", "Majid", "birth_year", "63");
        triples.write(t1, t2);
//        try {
//            triples.write(new Triple("context", "Hossein", "birth_year", "64"));
//            assert false;
//        } catch (Throwable th) {
//            assert true;
//        }
        t1.getSources().add(new Source("Test", 0, null, null, null));
        triples.write(t1);
        assert triples.search(null, null, "birth_year", null, 0, 0).getTotalSize() == 2;
        assert triples.search(null, "Hossein", "birth_year", null, 0, 0).getTotalSize() == 1;
        assert triples.randomSubjectForExpert(null,"web", "hossein", null, 20) != null;
        triples.delete(t1, t2);
    }

    @Test
    public void mappingTest() {
        final TemplateMapping m1 = new TemplateMapping("template");
        final TemplateMapping m2 = new TemplateMapping("template2");
        mappings.write(m1, m2);
        try {
            mappings.write(new TemplateMapping("template"));
            assert false;
        } catch (Throwable ignored) {
        }
        assert mappings.readTemplate(false, null, 0, 0).getData().contains(m1);
        assert !mappings.readTemplate(true, null, 0, 0).getData().contains(m1);
        m1.getRules().add(new MapRule("rdf:type", "dbo:template", ValueType.Resource));
        mappings.write(m1);
        assert !mappings.readTemplate(false, null, 0, 0).getData().contains(m1);
        assert mappings.readTemplate(true, null, 0, 0).getData().contains(m1);
        m1.getProperties().add(new PropertyMapping("template", "name"));
        m1.getProperties().get(0).getRules().add(new MapRule("rdf:type", ValueType.Resource));
        m1.getProperties().add(new PropertyMapping("template", "family"));
        m2.getProperties().add(new PropertyMapping("template2", "family"));
        mappings.write(m1, m2);
        assert mappings.searchProperty("template", null, 0, 1).getTotalSize() == 3;
        assert mappings.searchProperty("template2", null, 0, 1).getTotalSize() == 1;
        assert mappings.searchPredicate("ty", 10).size() > 0;
        mappings.delete(m1, m2);
    }

    @Test
    public void versionTest() {
        final Version version1 = new Version("test");
        versions.write(version1);
        try {
            versions.write(new Version("test"));
            assert false;
        } catch (Throwable ignored) {
        }
        assert versions.readAll().size() == 1;
        assert versions.readByModule("test").getId().equals(version1.getId());
        versions.delete(version1);
        assert versions.readAll().size() == 0;
    }

    @Test
    public void ontologyTest() {
        final Ontology o = new Ontology("context","a", "parent", new TypedValue(ValueType.Resource, "b"));
        ontology.write(o);
        try {
            ontology.write(new Ontology("context","a", "parent", new TypedValue(ValueType.Resource, "b")));
            assert false;
        }catch (Throwable ignored) {}
        assert ontology.search(null, null, null, null, 0, 0).getTotalSize() == 1;
        assert ontology.search(null, null, null, "b", 0, 0).getTotalSize() == 1;
        assert ontology.search(null, null, null, "bc", 0, 0).getTotalSize() == 0;
        ontology.delete(o);
        assert ontology.search(null, null, null, null, 0, 0).getTotalSize() == 0;

    }
}