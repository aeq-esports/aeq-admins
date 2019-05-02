package de.esports.aeq.admins.member.web.controller;

import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.web.dto.MemberDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static de.esports.aeq.admins.common.ExceptionResponseWrapper.notFound;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final ModelMapper mapper;
    private final MemberService memberService;

    @Autowired
    public MemberController(ModelMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }

    //-----------------------------------------------------------------------

    @GetMapping
    @ResponseBody
    public Collection<MemberDto> findAll() {
        return memberService.getMembers().stream()
                .map(this::toMemberDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public MemberDto findByUserId(@PathVariable("userId") Long userId) {
        return memberService.getMemberById(userId)
                .map(this::toMemberDto).orElseThrow(() -> notFound(userId));
    }

    @GetMapping("/byUsername/{username}")
    @ResponseBody
    public MemberDto findByUsername(@PathVariable("username") String username) {
        return memberService.getMemberByUsername(username)
                .map(this::toMemberDto).orElseThrow(() -> notFound(username));
    }

    //-----------------------------------------------------------------------

    private MemberDto toMemberDto(Member member) {
        return mapper.map(member, MemberDto.class);
    }
}
