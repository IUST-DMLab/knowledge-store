package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import javax.xml.bind.annotation.XmlType;

/**
 * Vote object for each expert
 */
@XmlType(name = "ExpertVote", namespace = "http://kg.dml.iust.ac.ir")
public class ExpertVote {
    private String module;
    private String expert;
    private Vote vote;

    public ExpertVote() {
    }

    public ExpertVote(String module, String expert, Vote vote) {
        this.module = module;
        this.expert = expert;
        this.vote = vote;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
