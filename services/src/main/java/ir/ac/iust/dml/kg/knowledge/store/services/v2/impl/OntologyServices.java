package ir.ac.iust.dml.kg.knowledge.store.services.v2.impl;


import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.IOntologyDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Ontology;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.TripleState;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.IOntologyServices;
import ir.ac.iust.dml.kg.knowledge.store.services.v2.data.OntologyData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * impl {@link IOntologyServices}
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v2.IOntologyServices")
public class OntologyServices implements IOntologyServices {
    @Autowired
    private IOntologyDao dao;

    @Override
    public OntologyData insert(@Valid OntologyData data) {
        if (data.getContext() == null) data.setContext("http://fkg.iust.ac.ir/");
        final Ontology old = dao.read(data.getContext(), data.getSubject(), data.getPredicate(), data.getObject().getValue());
        final Ontology ontology = data.fill(old);
        dao.write(ontology);
        return new OntologyData().sync(ontology);
    }

    @Override
    public List<OntologyData> batchInsert(@Valid List<OntologyData> data) {
        final List<OntologyData> ontology = new ArrayList<>();
        data.forEach(i-> ontology.add(insert(i)));
        return ontology;
    }

    @Override
    public Ontology remove(String context, String subject, String predicate, String object) {
        if (context == null) context = "http://fkg.iust.ac.ir/";
        Ontology ontology = dao.read(context, subject, predicate, object);
        if (ontology != null)
            dao.delete(ontology);
        return ontology;
    }

    @Override
    public Ontology ontology(String context, String subject, String predicate, String object) {
        if (context == null) context = "http://fkg.iust.ac.ir/";
        return dao.read(context, subject, predicate, object);
    }

    @Override
    public PagingList<Ontology> search(String context, String subject, String predicate, String object, boolean approved, int page, int pageSize) {
        return dao.search(context, subject, predicate, object, approved ? TripleState.Approved : null, page, pageSize);
    }
}
