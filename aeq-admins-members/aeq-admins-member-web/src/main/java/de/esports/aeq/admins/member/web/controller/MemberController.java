package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.MemberProfile;
import de.esports.aeq.admins.member.api.service.MemberProfileService;
import de.esports.aeq.admins.member.web.dto.MemberProfileDto;
import de.esports.aeq.admins.security.domain.UserTa;
import de.esports.aeq.admins.security.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.CollectionUtils.peek;
import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;
import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final MemberProfileService profileService;

    @Autowired
    public MemberController(ModelMapper mapper, UserService userService,
            MemberProfileService profileService) {
        this.mapper = mapper;
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping
    @ResponseBody
    public Collection<MemberProfileDto> findAll() {
        List<MemberProfileDto> profiles = profileService.getProfiles().stream()
                .map(this::toMemberProfileDto).collect(Collectors.toList());
        resolveMissingFields(profiles);
        return profiles;
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public MemberProfileDto findByUserId(@PathVariable("userId") Long userId) {
        MemberProfileDto profile = profileService.getProfileByUserId(userId)
                .map(this::toMemberProfileDto).orElseThrow(() -> notFound(userId));
        resolveMissingFields(profile);
        return profile;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MemberProfileDto request) {
        MemberProfile profile = mapper.map(request, MemberProfile.class);
        profileService.createProfile(profile);
    }

    //-----------------------------------------------------------------------

    private MemberProfileDto toMemberProfileDto(MemberProfile member) {
        return mapper.map(member, MemberProfileDto.class);
    }

    private void resolveMissingFields(MemberProfileDto profile) {
        userService.findById(profile.getUserId()).ifPresent(user ->
                resolveMissingFieldsWithUser(profile, user));
    }

    private void resolveMissingFields(Collection<MemberProfileDto> profiles) {
        Set<Long> userIds = profiles.stream().map(MemberProfileDto::getUserId)
                .collect(Collectors.toSet());
        Map<Long, List<UserTa>> usersById =
                userService.findAllByIds(userIds).stream().collect(groupingBy(UserTa::getId));

        for (MemberProfileDto profile : profiles) {
            peek(usersById.get(profile.getUserId())).ifPresent(user ->
                    resolveMissingFieldsWithUser(profile, user));
        }
    }

    private void resolveMissingFieldsWithUser(MemberProfileDto profile, UserTa user) {
        profile.setUsername(user.getUsername());
    }
}
