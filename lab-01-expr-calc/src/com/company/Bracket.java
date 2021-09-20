package com.company;

public class Bracket implements Expression {
    private final boolean mIsLeft;

    Bracket(boolean isLeft) {
        mIsLeft = isLeft;
    }

    public boolean isLeft() {
        return mIsLeft;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return null;
    }
}
