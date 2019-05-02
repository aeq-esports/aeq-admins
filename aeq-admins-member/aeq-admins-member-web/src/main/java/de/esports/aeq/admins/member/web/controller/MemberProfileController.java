package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.MemberProfile;
import de.esports.aeq.admins.member.api.service.MemberProfileService;
import de.esports.aeq.admins.member.web.dto.MemberProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;

@RestController
@RequestMapping("/members/{memberId}/profile")
public class MemberProfileController {

    private final ModelMapper mapper;
    private final MemberProfileService profileService;

    @Autowired
    public MemberProfileController(ModelMapper mapper, MemberProfileService profileService) {
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
        return profileService.getProfileByUserId(userId)
                .map(this::toMemberProfileDto).orElseThrow(() -> notFound(userId));
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
}
