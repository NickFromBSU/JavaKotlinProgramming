package com.company;

public interface BinaryOperation extends Operation {
    Expression getLeft();
    Expression getRight();
    void setOperands(Expression l, Expression r);
    BinaryOperationType getOperationType();
}
