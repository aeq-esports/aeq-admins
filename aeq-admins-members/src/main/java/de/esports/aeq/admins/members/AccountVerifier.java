package de.esports.aeq.admins.members;

import de.esports.aeq.admins.members.domain.VerifiableAccount;
import de.esports.aeq.admins.members.domain.exception.AccountVerificationException;

public interface AccountVerifier {

    /**
     * Obtains the type that this verifier can process.
     * <p>
     * It is guaranteed that only accounts matching the specified type are passed to this verifier.
     * If an account does not match the required type, the verifier will be skipped.
     *
     * @return the type that this verifier can process
     */
    String getType();

    /**
     * Verifies this account.
     * <p>
     * If the result of the verification process is positive, the accounts verified status must be
     * set to <code>true</code>. If the verification turn out to be negative, the accounts verified
     * status must be set to to <code>false</code>. If this verifier cannot determine either of
     * those two results, an {@link AccountVerificationException} must be thrown.
     *
     * @param account the account to be verified
     * @throws AccountVerificationException if the verifier cannot decide on the resulting
     *                                      verification status
     */
    void verify(VerifiableAccount account) throws AccountVerificationException;
}
