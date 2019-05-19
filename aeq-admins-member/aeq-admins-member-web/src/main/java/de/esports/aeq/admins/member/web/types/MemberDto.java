package de.esports.aeq.admins.member.web.types;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class MemberDto {

    private Long memberId;
    private String username;
    private boolean isTrialMember;
    private boolean isBanned;
    private Collection<String> roles = new ArrayList<>();
    private Instant createdAt;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isTrialMember() {
        return isTrialMember;
    }

    public void setTrialMember(boolean trialMember) {
        isTrialMember = trialMember;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
