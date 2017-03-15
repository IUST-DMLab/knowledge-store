package ir.ac.iust.dml.kg.knowledge.store.access.test;

import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for access
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context.xml")
public class AccessTest {
    @Autowired
    ITripleDao triples;

    @Test
    public void testTripleDao() {
        Triple t1 = new Triple("Hossein", "birth_year", "64");
        Triple t2 = new Triple("Majid", "birth_year", "63");
        triples.write(t1, t2);
        try {
            triples.write(new Triple("Hossein", "birth_year", "64"));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        t1.getSources().add("test");
        triples.write(t1);
        assert triples.search(null, "birth_year", null, 0, 0).getTotalSize() == 2;
        assert triples.search("Hossein", "birth_year", "64", 0, 0).getTotalSize() == 1;
        triples.delete(t1, t2);
    }
}
