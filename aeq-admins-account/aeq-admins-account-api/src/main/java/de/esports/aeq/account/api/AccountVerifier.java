package de.esports.aeq.account.api;


import de.esports.aeq.account.api.exception.AccountVerificationException;

public interface AccountVerifier {

    /**
     * Validates if this verifier can process the target <code>type</code>.
     * <p>
     * It is guaranteed that only accounts matching the specified type are passed to this verifier.
     * If an account does not match the required type, the verifier will be skipped.
     *
     * @return the type that this verifier can process
     * @see AccountId#getType()
     */
    boolean canVerify(String type);

    /**
     * Verifies this account.
     * <p>
     * If the result of the verification process is positive, the accounts verified status must be
     * set to <code>true</code>. If the verification turn out to be negative, the accounts verified
     * status must be set to to <code>false</code>. If this verifier cannot determine either of
     * those two results, an {@link AccountVerificationException} must be thrown.
     *
     * @param account the account to be verified
     * @param token   the token to be used for verification
     * @throws AccountVerificationException if the verifier cannot decide on the resulting
     *                                      verification status
     */
    void verify(VerifiableAccount account, Object token) throws AccountVerificationException;
}
