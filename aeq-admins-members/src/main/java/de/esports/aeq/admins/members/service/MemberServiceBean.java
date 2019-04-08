package de.esports.aeq.admins.members.service;

import de.esports.aeq.account.api.Account;
import de.esports.aeq.account.api.AccountId;
import de.esports.aeq.account.api.AccountType;
import de.esports.aeq.account.api.exception.InvalidAccountIdTypeException;
import de.esports.aeq.account.api.exception.MissingAccountIdException;
import de.esports.aeq.account.api.jpa.AccountTa;
import de.esports.aeq.account.api.service.AccountService;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.domain.Member;
import de.esports.aeq.admins.members.jpa.MemberRepository;
import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class MemberServiceBean implements MemberService {

    private static final String MEMBER_TYPE = AccountType.MEMBER.toString();

    private final ModelMapper mapper;
    private final MemberRepository repository;
    private final AccountService accountService;

    @Autowired
    public MemberServiceBean(ModelMapper mapper, MemberRepository repository,
            AccountService accountService) {
        this.mapper = mapper;
        this.repository = repository;
        this.accountService = accountService;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setup() {
        // setup mapper
        mapper.addConverter(this::mapMember, MemberTa.class, Member.class);
        mapper.addConverter(this::mapMemberTa, Member.class, MemberTa.class);
    }

    //-----------------------------------------------------------------------

    /*
     * Converters used by the mapper.
     */

    private Member mapMember(MappingContext<MemberTa, Member> context) {
        MemberTa source = context.getSource();

        Member member = new Member();
        mapper.map(source.getAccount(), member);

        source.getAccounts().stream()
                .map(account -> mapper.map(account, Account.class))
                .forEach(member::addAccount);

        return member;
    }

    private MemberTa mapMemberTa(MappingContext<Member, MemberTa> context) {
        Member source = context.getSource();

        MemberTa memberTa = new MemberTa();
        AccountTa accountTa = new AccountTa();
        mapper.map(source, accountTa);
        memberTa.setAccount(accountTa);

        List<AccountTa> accounts = source.getAccounts().stream()
                .map(account -> mapper.map(account, AccountTa.class))
                .collect(Collectors.toList());
        memberTa.setAccounts(accounts);

        return memberTa;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Member> getMembers() {
        return repository.findAll().stream().map(this::toMember)
                .collect(Collectors.toList());
    }

    @Override
    public Member getMemberById(Long id) {
        requireNonNull(id);
        return repository.findById(id).map(this::toMember)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Member getMemberByAccountId(AccountId accountId) {
        requireNonNull(accountId);
        assertMemberAccountId(accountId);

        return repository.findByAccountAccountId(accountId).map(this::toMember)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
    }

    @Override
    public Member createMember(Member member) {
        requireNonNull(member);

        assertMemberAccountId(member.getAccountId());

        // use the existing account facility to not bypass any required logic
        accountService.createAccount(member);
        member.getAccounts().forEach(accountService::createAccount);

        MemberTa entity = toMemberTa(member);
        repository.save(entity);

        return toMember(entity);
    }

    @Override
    public Member updateMember(Member member) {
        requireNonNull(member);

        assertMemberAccountId(member.getAccountId());

        Long memberId = member.getId();
        MemberTa existing = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(memberId));

        MemberTa update = toMemberTa(member);
        mapper.map(update, existing);

        repository.save(existing);

        return toMember(existing);
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


    //-----------------------------------------------------------------------

    /*
     * Convenience methods to be used for mapping.
     */

    private Member toMember(MemberTa member) {
        return mapper.map(member, Member.class);
    }

    private MemberTa toMemberTa(Member member) {
        return mapper.map(member, MemberTa.class);
    }
}
