package de.esports.aeq.admins.member.web.controller;

import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;

import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.service.UserProfileService;
import de.esports.aeq.admins.member.web.types.MemberProfileDto;
import java.util.Collection;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
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
    //@PreAuthorize("hasAuthority('READ_USER_PROFILE')")
    public Collection<MemberProfileDto> findAll() {
        return profileService.getAll().stream()
            .map(this::toMemberProfileDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('READ_USER_PROFILE') or @cse.hasUserId(#userId)")
    public MemberProfileDto findByUserId(@PathVariable("userId") Long userId) {
        return profileService.getOneById(userId)
            .map(this::toMemberProfileDto).orElseThrow(() -> notFound(userId));
    }

    //-----------------------------------------------------------------------

    private MemberProfileDto toMemberProfileDto(UserProfile member) {
        return mapper.map(member, MemberProfileDto.class);
    }
}
