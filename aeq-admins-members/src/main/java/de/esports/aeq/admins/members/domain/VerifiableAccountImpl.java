package de.esports.aeq.admins.members.domain;

import java.time.Instant;
import java.util.Optional;

public class VerifiableAccountImpl implements VerifiableAccount {

    private String id;
    private Instant createdAt;
    private Instant lastSeenAt;
    private boolean verified;
    private Instant verifiedAt;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Optional<Instant> getLastSeenAt() {
        return Optional.ofNullable(lastSeenAt);
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
