package ir.ac.iust.dml.kg.knowledge.store.access.test;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.IMappingDao;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.*;
import ir.ac.iust.dml.kg.knowledge.store.access.stats.KeyCount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Unit test for access
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context.xml")
public class AccessTest {
    @Autowired
    ITripleDao triples;
    @Autowired
    IMappingDao mappings;

    @Test
    public void testTripleDao() {
        Triple t1 = new Triple("context", "Hossein", "birth_year", new TypedValue(ValueType.Short, "64"));
        Triple t2 = new Triple("context", "Majid", "birth_year", new TypedValue(ValueType.Short, "63"));
        triples.write(t1, t2);
        try {
            triples.write(new Triple("context", "Hossein", "birth_year", new TypedValue(ValueType.Short, "64")));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        t1.getSources().add(new Source("Test", null, null, null));
        triples.write(t1);
        assert triples.search(null, null, "birth_year", null, 0, 0).getTotalSize() == 2;
        assert triples.search(null, "Hossein", "birth_year", "64", 0, 0).getTotalSize() == 1;
        assert triples.randomTripleForExpert("web", "hossein", 2) != null;
        triples.delete(t1, t2);
    }

    @Test
    public void randomTest() {
        PagingList<KeyCount> subjects = triples.searchSubjectForExpert("wikipedia/infobox", "web", "hossein", "/c", 0, 2);
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            List<Triple> x = triples.randomTripleForExpert("web", "hossein", 30);
            assert !x.isEmpty();
            System.out.println(System.currentTimeMillis());
        }
        System.out.println((System.currentTimeMillis() - s1) / 500);
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
        m1.getProperties().add(new PropertyMapping("template", "family"));
        m2.getProperties().add(new PropertyMapping("template2", "family"));
        mappings.write(m1, m2);
        try {
            m1.getProperties().add(new PropertyMapping("template", "family"));
            assert false;
        } catch (Throwable ignored) {
        }

        assert mappings.searchProperty("template", null, 0, 1).getTotalSize() == 3;
        assert mappings.searchProperty("template2", null, 0, 1).getTotalSize() == 1;
        assert mappings.searchPredicate("ty", 10).size() > 0;
        mappings.delete(m1, m2);
    }
}
