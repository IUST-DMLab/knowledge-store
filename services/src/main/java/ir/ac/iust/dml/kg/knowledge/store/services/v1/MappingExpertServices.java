package ir.ac.iust.dml.kg.knowledge.store.services.v1;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.PropertyMapping;
import ir.ac.iust.dml.kg.knowledge.store.access.entities.TemplateMapping;
import ir.ac.iust.dml.kg.knowledge.store.services.v1.data.MapRuleData;

import javax.jws.WebService;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by HosseiN on 07/05/2017.
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.v1.IMappingExpertServices")
public class MappingExpertServices implements IMappingExpertServices {
    @Override
    public PagingList<PropertyMapping> searchProperty(String template, String property, int page, int pageSize) {
        return null;
    }

    @Override
    public PagingList<TemplateMapping> searchTemplate(String template, int page, int pageSize) {
        return null;
    }

    @Override
    public List<String> predicates(String keyword) {
        return null;
    }

    @Override
    public Boolean insert(String template, String property, @Valid MapRuleData data) {
        return null;
    }

    @Override
    public Boolean delete(String template, String property, @Valid MapRuleData data) {
        return null;
    }
}
