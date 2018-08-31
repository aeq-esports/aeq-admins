package de.esports.aeq.admins.common;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.function.Function;
import java.util.function.Supplier;

public class BooleanExpressionBuilder<T> {

    private BooleanExpression expression;

    public BooleanExpressionBuilder<T> and(Object object, BooleanExpression expression) {
        if (object != null) {
            this.expression = this.expression == null ? expression :
                    this.expression.and(expression);
        }
        return this;
    }

    public T mapOrElse(Function<BooleanExpression, T> function, Supplier<T> supplier) {
        if (expression == null) {
            return supplier.get();
        }
        return function.apply(expression);
    }

    public BooleanExpression get() {
        return expression;
    }
}
