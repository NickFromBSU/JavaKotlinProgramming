package com.company;

public class LiteralImpl implements Literal {
    private final double mValue;

    LiteralImpl(double value) {
        mValue = value;
    }

    @Override
    public double getValue() {
        return mValue;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public String toString() {
        return (String) accept(ToStringVisitor.INSTANCE);
    }
}
