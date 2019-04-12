package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.AccountType;
import de.esports.aeq.admins.common.MapperProvider;
import de.esports.aeq.admins.member.api.Member;
import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import de.esports.aeq.admins.platform.api.PlatformReference;
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
        // setup mapper
        mapper.createTypeMap(Member.class, MemberTa.class)
                .addMappings(m -> m.using(this::mapAccountId).map(Member::getAccountId,
                        MemberTa::setId));
        mapper.createTypeMap(MemberTa.class, Member.class)
                .addMappings(m -> m.using(this::mapDatabaseId).map(MemberTa::getId,
                        Member::setAccountId));
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    private Object mapAccountId(MappingContext<Object, Object> context) {
        Member source = (Member) context.getSource();
        return toDatabaseId(source.getAccountId());
    }

    private Object mapDatabaseId(MappingContext<Object, Object> context) {
        MemberTa source = (MemberTa) context.getSource();
        return toAccountId(source.getId());
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
        AccountId mappedId = new AccountId(value, AccountType.MEMBER.toString());
        mappedId.setPlatformReference(PlatformReference.system());
        return mappedId;
    }
}
