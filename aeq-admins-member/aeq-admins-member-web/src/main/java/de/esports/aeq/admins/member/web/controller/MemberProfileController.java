package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.service.UserProfileService;
import de.esports.aeq.admins.member.web.types.MemberProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;

@RestController
@RequestMapping("/members/{memberId}/profile")
public class MemberProfileController {

    private final ModelMapper mapper;
    private final UserProfileService profileService;

    @Autowired
    public MemberProfileController(ModelMapper mapper, UserProfileService profileService) {
        this.mapper = mapper;
        this.profileService = profileService;
    }

    //-----------------------------------------------------------------------

    @GetMapping
    @ResponseBody
    public Collection<MemberProfileDto> findAll() {
        return profileService.getProfiles().stream()
                .map(this::toMemberProfileDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public MemberProfileDto findByUserId(@PathVariable("userId") Long userId) {
        return profileService.getOneByUsername(userId)
                .map(this::toMemberProfileDto).orElseThrow(() -> notFound(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MemberProfileDto request) {
        UserProfile profile = mapper.map(request, UserProfile.class);
        profileService.createProfile(profile);
    }

    //-----------------------------------------------------------------------

    private MemberProfileDto toMemberProfileDto(UserProfile member) {
        return mapper.map(member, MemberProfileDto.class);
    }
}
