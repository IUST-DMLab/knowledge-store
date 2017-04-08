package ir.ac.iust.dml.kg.knowledge.store.access.entities;

/**
 * Vote object for each expert
 */
public class ExpertVote {
    private String module;
    private String identifier;
    private Vote vote;

    public ExpertVote() {
    }

    public ExpertVote(String module, String identifier, Vote vote) {
        this.module = module;
        this.identifier = identifier;
        this.vote = vote;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
