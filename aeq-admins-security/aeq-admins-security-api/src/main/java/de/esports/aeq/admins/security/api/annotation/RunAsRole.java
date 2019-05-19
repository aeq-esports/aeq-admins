package de.esports.aeq.admins.security.api.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RunAsRole {

    String value();
}
