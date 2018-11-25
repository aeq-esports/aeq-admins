package de.esports.aeq.admins.common;

import java.lang.annotation.*;

/**
 * Indicates that the annotated element is used by Camunda.
 * <p>
 * This annotation should remind developers that if any changes are made to the target signature,
 * dependent expressions in diagrams might need to be updated to function properly.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface CamundaRelated {

}
