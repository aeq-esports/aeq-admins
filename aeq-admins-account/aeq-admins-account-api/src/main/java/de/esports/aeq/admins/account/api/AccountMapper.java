package de.esports.aeq.admins.account.api;

import de.esports.aeq.admins.account.api.jpa.entity.AccountIdTa;
import de.esports.aeq.admins.account.api.jpa.entity.AccountTa;
import de.esports.aeq.admins.platform.api.Platform;
import de.esports.aeq.admins.platform.api.jpa.PlatformTa;
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
        destination.setType(source.getValueType());

        PlatformTa platformTa = mapper.map(source.getPlatform(), PlatformTa.class);
        destination.setPlatform(platformTa);

        return destination;
    }

    private AccountId mapAccountIdTa(MappingContext<AccountIdTa, AccountId> context) {
        AccountIdTa source = context.getSource();
        Platform platform = mapper.map(source.getPlatform(), Platform.class);
        return new AccountId(source.getValue(), source.getType(), platform);
    }

    //-----------------------------------------------------------------------

    /*
     * Convenience methods.
     */

    public AccountIdTa toAccountIdTa(AccountId accountId) {
        return mapper.map(accountId, AccountIdTa.class);
    }

    public AccountTa toAccountTa(Account account) {
        return mapper.map(account, AccountTa.class);
    }

}
