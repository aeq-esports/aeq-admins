package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountType;
import de.esports.aeq.admins.common.MapperProvider;
import de.esports.aeq.admins.member.api.ConnectedAccount;
import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.member.impl.jpa.entity.ConnectedAccountTa;
import de.esports.aeq.admins.member.impl.jpa.entity.MemberTa;
import de.esports.aeq.admins.platform.api.Platforms;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MemberMapper implements MapperProvider {

    private final ModelMapper mapper;

    public MemberMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setup() {
        mapper.addConverter(this::mapConnectedAccount);
        mapper.addConverter(this::mapConnectedAccountTa);
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    private ConnectedAccountTa mapConnectedAccount(
            MappingContext<ConnectedAccount, ConnectedAccountTa> context) {
        return toConnectedAccountTa(context.getSource());
    }

    private ConnectedAccount mapConnectedAccountTa(
            MappingContext<ConnectedAccountTa, ConnectedAccount> context) {
        return toConnectedAccount(context.getSource());
    }

    //-----------------------------------------------------------------------

    /*
     * Converters.
     */

    public Member toMember(MemberTa member) {
        return mapper.map(member, Member.class);
    }

    public MemberTa toMemberTa(Member member) {
        return mapper.map(member, MemberTa.class);
    }

    public Long toDatabaseId(AccountId accountId) {
        return Long.valueOf(accountId.getValue());
    }

    private AccountId toAccountId(Long accountId) {
        String value = String.valueOf(accountId);
        return new AccountId(value, AccountType.MEMBER.toString(), Platforms.SYSTEM);
    }

    public ConnectedAccountTa toConnectedAccountTa(ConnectedAccount account) {
        return mapper.map(account, ConnectedAccountTa.class);
    }

    public ConnectedAccount toConnectedAccount(ConnectedAccountTa account) {
        return mapper.map(account, ConnectedAccount.class);
    }

}
