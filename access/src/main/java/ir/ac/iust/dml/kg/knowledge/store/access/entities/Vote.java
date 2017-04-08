package ir.ac.iust.dml.kg.knowledge.store.access.entities;

import javax.xml.bind.annotation.XmlType;

/**
 * Vote type of triple
 */
@XmlType(name = "Vote", namespace = "http://kg.dml.iust.ac.ir")
public enum Vote {
    None, Reject, Approve, VIPReject, VIPApprove
}
