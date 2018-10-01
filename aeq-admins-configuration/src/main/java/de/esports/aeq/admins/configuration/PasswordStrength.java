package de.esports.aeq.admins.configuration;

public class PasswordStrength {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requirePunctuation;
    private boolean requireNumbers;

    public PasswordStrength(int minLength, boolean requireUppercase,
            boolean requireLowercase, boolean requirePunctuation, boolean requireNumbers) {
        this.minLength = minLength;
        this.requireUppercase = requireUppercase;
        this.requireLowercase = requireLowercase;
        this.requirePunctuation = requirePunctuation;
        this.requireNumbers = requireNumbers;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public boolean isRequireUppercase() {
        return requireUppercase;
    }

    public void setRequireUppercase(boolean requireUppercase) {
        this.requireUppercase = requireUppercase;
    }

    public boolean isRequireLowercase() {
        return requireLowercase;
    }

    public void setRequireLowercase(boolean requireLowercase) {
        this.requireLowercase = requireLowercase;
    }

    public boolean isRequirePunctuation() {
        return requirePunctuation;
    }

    public void setRequirePunctuation(boolean requirePunctuation) {
        this.requirePunctuation = requirePunctuation;
    }

    public boolean isRequireNumbers() {
        return requireNumbers;
    }

    public void setRequireNumbers(boolean requireNumbers) {
        this.requireNumbers = requireNumbers;
    }
}
