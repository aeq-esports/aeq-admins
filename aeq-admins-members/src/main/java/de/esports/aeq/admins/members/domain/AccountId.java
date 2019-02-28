package de.esports.aeq.admins.members.domain;

public final class AccountId {

    private String value;
    private String type;

    public static AccountId of(String value, String type) {
        return new AccountId(value, type);
    }

    private AccountId(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
