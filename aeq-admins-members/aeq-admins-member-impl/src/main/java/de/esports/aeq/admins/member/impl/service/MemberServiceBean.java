package de.esports.aeq.admins.member.impl.service;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountMapper;
import de.esports.aeq.admins.account.api.AccountType;
import de.esports.aeq.admins.account.api.exception.AccountIdException;
import de.esports.aeq.admins.account.api.exception.InvalidAccountIdTypeException;
import de.esports.aeq.admins.account.api.exception.MissingAccountIdException;
import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.member.api.ConnectedAccount;
import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.api.MemberAccount;
import de.esports.aeq.admins.member.api.service.ConnectedAccountService;
import de.esports.aeq.admins.member.api.service.MemberService;
import de.esports.aeq.admins.member.impl.MemberMapper;
import de.esports.aeq.admins.member.impl.jpa.MemberRepository;
import de.esports.aeq.admins.member.impl.jpa.entity.MemberTa;
import de.esports.aeq.admins.platform.api.Platforms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class MemberServiceBean implements MemberService {

    private static final String MEMBER_TYPE = AccountType.MEMBER.toString();

    private final MemberMapper memberMapper;
    private final AccountMapper accountMapper;
    private final MemberRepository repository;
    private final ConnectedAccountService connectedAccountService;

    @Autowired
    public MemberServiceBean(MemberMapper memberMapper, AccountMapper accountMapper,
            MemberRepository repository,
            ConnectedAccountService connectedAccountService) {
        this.memberMapper = memberMapper;
        this.accountMapper = accountMapper;
        this.repository = repository;
        this.connectedAccountService = connectedAccountService;
    }

    //-----------------------------------------------------------------------

    @Override
    public Collection<Member> getMembers() {
        return repository.findAll().stream().map(memberMapper::toMember)
                .collect(Collectors.toList());
    }

    @Override
    public Member getMemberById(Long id) {
        requireNonNull(id);
        return repository.findById(id).map(memberMapper::toMember)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public Member getMemberByAccountId(AccountId accountId) {
        requireMemberAccountId(accountId);
        AccountIdTa accountIdTa = accountMapper.toAccountIdTa(accountId);
        return repository.findByAccountAccountId(accountIdTa).map(memberMapper::toMember)
                .orElseThrow(() -> new EntityNotFoundException(accountId));
    }

    @Override
    public Member createMember(Member member) {
        requireNonNull(member);

        MemberAccount account = new MemberAccount();
        AccountId accountId = new AccountId(UUID.randomUUID().toString(), MEMBER_TYPE,
                Platforms.SYSTEM);
        account.setAccountId(accountId);
        member.setAccount(account);

        requireMemberAccountId(accountId);
        requireUniqueConnectedAccounts(member.getConnectedAccounts());

        MemberTa entity = memberMapper.toMemberTa(member);
        repository.save(entity);

        return memberMapper.toMember(entity);
    }

    @Override
    public Member updateMember(Member member) {
        requireNonNull(member);

        Account account = requireNonNull(member.getAccount());
        AccountId accountId = requireNonNull(account.getAccountId());

        requireMemberAccountId(accountId);

        Long memberId = memberMapper.toDatabaseId(accountId);
        MemberTa existing = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(memberId));

        Set<ConnectedAccount> accounts = new HashSet<>(member.getConnectedAccounts());
        Set<ConnectedAccount> connectedAccounts = existing.getConnectedAccounts().stream()
                .map(memberMapper::toConnectedAccount).collect(Collectors.toSet());
        // remove already existing accounts that are connected to this member
        accounts.removeAll(connectedAccounts);
        requireUniqueConnectedAccounts(accounts);

        MemberTa update = memberMapper.toMemberTa(member);
        memberMapper.getMapper().map(update, existing);

        repository.save(existing);

        return memberMapper.toMember(existing);
    }

    @Override
    public void deleteMember(Long id) {
        repository.deleteById(requireNonNull(id));
    }

    //-----------------------------------------------------------------------

    /*
     * Assertion methods.
     */

    private void requireMemberAccountId(AccountId accountId) {
        if (accountId == null) {
            throw new MissingAccountIdException();
        }

        String type = accountId.getValueType();
        if (!MEMBER_TYPE.equals(type)) {
            throw new InvalidAccountIdTypeException(MEMBER_TYPE, type);
        }
    }

    private void requireUniqueConnectedAccounts(Collection<ConnectedAccount> accounts) {
        Set<ConnectedAccount> accountsSet = new HashSet<>(accounts);
        boolean isConnected = connectedAccountService.isAlreadyConnected(accountsSet);
        if (isConnected) {
            throw new AccountIdException("An account id is already occupied");
        }
    }

}
