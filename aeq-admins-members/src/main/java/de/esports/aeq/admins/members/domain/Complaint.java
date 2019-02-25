package de.esports.aeq.admins.members.domain;

import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;

public class Complaint implements Serializable {

    private Long id;
    private AccountId accuserAccountId;
    private Collection<AccountId> accusedAccountIds;
    private Instant createdAt;
    private String description;
    private Collection<URL> attachments;

}
