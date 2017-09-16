package ir.ac.iust.dml.kg.knowledge.store.access2.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import ir.ac.iust.dml.kg.knowledge.store.access2.dao.ISubjectDao;
import ir.ac.iust.dml.kg.knowledge.store.access2.entities.Subject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SubjectDaoImpl2 implements ISubjectDao {
    private final MongoOperations op;

    @Autowired
    public SubjectDaoImpl2(@Qualifier("store2") MongoOperations op) {
        this.op = op;
    }

    @Override
    public void write(Subject... subjects) {
        for (Subject s : subjects) {
            s.setModificationEpoch(System.currentTimeMillis());
            op.save(s);
        }
    }

    @Override
    public void delete(Subject... subjects) {
        for (Subject s : subjects)
            op.remove(s);
    }

    @Override
    public Subject read(ObjectId id) {
        return op.findOne(
                new Query().addCriteria(Criteria.where("id").is(id)),
                Subject.class
        );
    }

    @Override
    public Subject read(String context, String subject) {
        return op.findOne(
                new Query()
                        .addCriteria(Criteria.where("context").is(context))
                        .addCriteria(Criteria.where("subject").is(subject)),
                Subject.class
        );
    }

    @Override
    public Subject randomSubjectForExpert(String source, String voter) {
        final List<BasicDBObject> sampleQuery = new ArrayList<>();
        if (source != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("sourcesNeedVote", source)));
        else
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("sourcesNeedVote", new BasicDBObject(" $exists", true))));
        if (voter != null)
            sampleQuery.add(new BasicDBObject("$match", new BasicDBObject("voters", new BasicDBObject("$ne", voter))));
        sampleQuery.add(new BasicDBObject("$sample", new BasicDBObject("size", 1)));
        final Iterator<DBObject> subjectAggregate = op.getCollection("subjects").aggregate(sampleQuery).results().iterator();
        if (!subjectAggregate.hasNext()) return null;
        return op.getConverter().read(Subject.class, subjectAggregate.next());
    }
}
