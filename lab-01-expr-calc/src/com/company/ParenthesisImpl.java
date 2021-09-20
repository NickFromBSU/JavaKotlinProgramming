package com.company;

public class ParenthesisImpl implements Parenthesis {
    private Expression operand;

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Expression getOperand() {
        return operand;
    }

    @Override
    public void setOperand(Expression operand_arg) {
        operand = operand_arg;
    }

    @Override
    public String toString() {
        return (String) accept(ToStringVisitor.INSTANCE);
    }
}
