package de.esports.aeq.admins.member.impl.service;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountType;
import de.esports.aeq.admins.account.api.exception.InvalidAccountIdTypeException;
import de.esports.aeq.admins.account.api.exception.MissingAccountIdException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.member.impl.MemberRepository;
import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class MemberServiceBean implements MemberService {

    private static final String MEMBER_TYPE = AccountType.MEMBER.toString();

    private final MemberMapper mapper;
    private final MemberRepository repository;

    @Autowired
    public MemberServiceBean(MemberMapper mapper, MemberRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Member> getMembers() {
        return repository.findAll().stream().map(mapper::toMember)
                .collect(Collectors.toList());
    }

    @Override
    public Member getMemberById(Long id) {
        requireNonNull(id);
        return repository.findById(id).map(mapper::toMember)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Member getMemberByAccountId(AccountId accountId) {
        requireNonNull(accountId);
        assertMemberAccountId(accountId);

        return repository.findByAccountAccountId(accountId).map(mapper::toMember)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
    }

    @Override
    public Member createMember(Member member) {
        requireNonNull(member);

        assertMemberAccountId(member.getAccountId());

        MemberTa entity = mapper.toMemberTa(member);
        repository.save(entity);

        return mapper.toMember(entity);
    }

    @Override
    public Member updateMember(Member member) {
        requireNonNull(member);

        assertMemberAccountId(member.getAccountId());

        Long memberId = mapper.toDatabaseId(member.getAccountId());
        MemberTa existing = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(memberId));

        MemberTa update = mapper.toMemberTa(member);
        mapper.getMapper().map(update, existing);

        repository.save(existing);

        return mapper.toMember(existing);
    }

    @Override
    public void deleteMember(Long id) {
        repository.deleteById(requireNonNull(id));
    }

    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void assertMemberAccountId(AccountId accountId) {
        if (accountId == null) {
            throw new MissingAccountIdException();
        }

        String type = accountId.getType();
        if (!MEMBER_TYPE.equals(type)) {
            throw new InvalidAccountIdTypeException(MEMBER_TYPE, type);
        }
    }

}
