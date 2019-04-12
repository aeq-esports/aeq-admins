package de.esports.aeq.admins.account.impl;

import de.esports.aeq.admins.account.api.Account;
import de.esports.aeq.admins.account.api.AccountId;
import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.api.jpa.entity.AccountTa;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AccountMapper {

    private final ModelMapper mapper;

    public AccountMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    //-----------------------------------------------------------------------

    @PostConstruct
    private void setup() {
        // setup mapper
        mapper.addConverter(this::mapAccountId, AccountId.class, AccountIdTa.class);
        mapper.addConverter(this::mapAccountIdTa, AccountIdTa.class, AccountId.class);
    }

    //-----------------------------------------------------------------------

    /*
     * Converters.
     */

    private AccountIdTa mapAccountId(MappingContext<AccountId, AccountIdTa> context) {
        AccountId source = context.getSource();

        AccountIdTa destination = new AccountIdTa();
        destination.setValue(source.getValue());
        destination.setType(source.getType());

        // TODO platform

        return destination;
    }

    private AccountId mapAccountIdTa(MappingContext<AccountIdTa, AccountId> context) {
        AccountIdTa source = context.getSource();

        AccountId destination = new AccountId(source.getValue(), source.getType());

        // TODO platform

        return destination;
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods.
     */

    public AccountIdTa toAccountIdTa(AccountId accountId) {
        return mapper.map(accountId, AccountIdTa.class);
    }

    public AccountImpl toAccount(AccountTa account) {
        return mapper.map(account, AccountImpl.class);
    }

    public AccountTa toAccountTa(Account account) {
        return mapper.map(account, AccountTa.class);
    }

}
