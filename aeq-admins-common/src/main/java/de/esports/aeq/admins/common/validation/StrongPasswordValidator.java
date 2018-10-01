package de.esports.aeq.admins.common.validation;

import de.esports.aeq.admins.configuration.PasswordStrength;
import de.esports.aeq.admins.configuration.SystemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private final PasswordStrength strength;

    private static final Pattern LOWERCASE = Pattern.compile("(?=.*[a-z])");
    private static final Pattern UPPERCASE = Pattern.compile("(?=.*[A-Z])");
    private static final Pattern PUNCTUATION = Pattern.compile("(?=.*[\\p{P}\\p{S}])");

    @Autowired
    public StrongPasswordValidator(SystemConfiguration configuration) {
        this.strength = configuration.getPasswordStrength();
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {
        return checkMinLength(password) && checkLowercase(password) && checkUppercase(password)
                && checkPunctuation(password);
    }

    private boolean checkMinLength(String password) {
        if (strength.getMinLength() > 0) {
            return password.length() >= strength.getMinLength();
        }
        return true;
    }

    private boolean checkLowercase(String password) {
        if (strength.isRequireLowercase()) {
            return LOWERCASE.matcher(password).find();
        }
        return true;
    }

    private boolean checkUppercase(String password) {
        if (strength.isRequireUppercase()) {
            return UPPERCASE.matcher(password).find();
        }
        return true;
    }

    private boolean checkPunctuation(String password) {
        if (strength.isRequirePunctuation()) {
            return PUNCTUATION.matcher(password).find();
        }
        return true;
    }
}
