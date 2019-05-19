package de.esports.aeq.admins.common;

public interface Referable {

    /**
     * Obtains the id of the referring entity.
     *
     * @return the id of the referring entity, may be <code>null</code>
     */
    String getReferralId();
}
