package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.MemberDetails;
import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.web.dto.CreateMemberDto;
import de.esports.aeq.admins.member.web.dto.MemberDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final ModelMapper mapper;
    private final MemberService memberService;

    public MemberController(ModelMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }

    @GetMapping
    @ResponseBody
    public Collection<MemberDto> findAll() {
        return memberService.getMembers().stream().map(this::toMemberDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateMemberDto request) {
        MemberDetails details = mapper.map(request, MemberDetails.class);
        Member member = new Member();
        member.setDetails(details);
        memberService.createMember(member);
    }

    private MemberDto toMemberDto(Member member) {
        return mapper.map(member, MemberDto.class);
    }
}
