package com.company;

public interface Expression {
    Object accept(ExpressionVisitor visitor);
}
