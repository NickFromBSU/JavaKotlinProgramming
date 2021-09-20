package com.company;

public class VariableImpl implements Variable {
    private final String mName;

    VariableImpl(String name) {
        this.mName = name;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitVariable(this);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return (String) accept(ToStringVisitor.INSTANCE);
    }
}
