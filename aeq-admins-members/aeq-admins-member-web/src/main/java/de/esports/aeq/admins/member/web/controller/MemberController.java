package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.MemberProfile;
import de.esports.aeq.admins.member.api.service.MemberProfileService;
import de.esports.aeq.admins.member.web.dto.MemberProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final ModelMapper mapper;
    private final MemberProfileService profileService;

    public MemberController(ModelMapper mapper, MemberProfileService profileService) {
        this.mapper = mapper;
        this.profileService = profileService;
    }

    @GetMapping
    @ResponseBody
    public Collection<MemberProfileDto> findAll() {
        return profileService.getProfiles().stream().map(this::toMemberProfileDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MemberProfileDto request) {
        MemberProfile profile = mapper.map(request, MemberProfile.class);
        profileService.createProfile(profile);
    }

    private MemberProfileDto toMemberProfileDto(MemberProfile member) {
        return mapper.map(member, MemberProfileDto.class);
    }
}
