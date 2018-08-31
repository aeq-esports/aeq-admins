package de.esports.aeq.admins.users.web;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.esports.aeq.admins.users.domain.QUserTa;


public class Predicates {

    public static BooleanExpression ts3UId(String ts3UId) {
        return QUserTa.userTa.ts3UId.eq(ts3UId);
    }

    public static BooleanExpression firstName(String firstName) {
        return QUserTa.userTa.details.firstName.eq(firstName);
    }
}
