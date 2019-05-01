package de.esports.aeq.admins.member.web.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class MemberDto {

    private Long memberId;
    private String nickname;
    private boolean isTrialMember;
    private boolean isBanned;
    private Collection<String> roles = new ArrayList<>();
    private Instant createdAt;
}
