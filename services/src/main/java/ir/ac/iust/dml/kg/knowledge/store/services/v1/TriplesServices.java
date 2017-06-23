package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.dao.ITripleDao;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.Triple;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TripleState;
import ir.ac.iust.dml.kg.knowledge.store.services.v1.data.TripleData;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * impl {@link ITriplesServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v1.ITriplesServices")
public class TriplesServices implements ITriplesServices {
    @Autowired
    private ITripleDao dao;

    @Override
    public Boolean insert(@Valid TripleData data) {
        if (data.getContext() == null) data.setContext("http://kg.dml.iust.ac.ir");
        final Triple oldTriple = dao.read(data.getContext(), data.getSubject(), data.getPredicate(), data.getObject().getValue());
        final Triple newTriple = data.fill(oldTriple);
        dao.write(newTriple);
        return true;
    }

    @Override
    public Boolean batchInsert(@Valid List<TripleData> data) {
        data.forEach(this::insert);
        return true;
    }

    @Override
    public Triple triple(String context, String subject, String predicate, String object) {
        if (context == null) context = "http://kg.dml.iust.ac.ir";
        return dao.read(context, subject, predicate, object);
    }

    @Override
    public PagingList<Triple> search(String context, String subject, String predicate, String object, int page, int pageSize) {
        return dao.search(context, subject, predicate, object, page, pageSize);
    }

    @Override
    public String export(TripleState state, ExportFormat format, Long epoch, int page, int pageSize) {
        final PagingList<Triple> triples = dao.read(state, epoch, page, pageSize);
        final ModelBuilder builder = new ModelBuilder();
        triples.getData().forEach(t ->
                builder.add(t.getSubject(), t.getPredicate(), t.getObject().createValue())
        );

        final Model model = builder.build();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final RDFWriter writer = Rio.createWriter(format.getRDFFormat(), out);
        writer.startRDF();
        writer.handleNamespace("kg", "http://knowledgegraph.ir/");
        model.forEach(writer::handleStatement);
        writer.endRDF();
        return out.toString();
    }
}
