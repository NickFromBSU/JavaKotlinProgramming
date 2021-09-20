package com.company;


public interface Parenthesis extends Operation {
    Expression getOperand();
    void setOperand(Expression operand);
}
