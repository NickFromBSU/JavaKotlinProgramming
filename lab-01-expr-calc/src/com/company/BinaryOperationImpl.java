package com.company;

public class BinaryOperationImpl implements BinaryOperation {
    private Expression mLeft;
    private Expression mRight;
    private final BinaryOperationType mType;
    private final int mPriority;

    BinaryOperationImpl(BinaryOperationType type, int priority) {
        mType = type;
        mPriority = priority;
    }

    @Override
    public Expression getLeft() {
        return mLeft;
    }

    @Override
    public Expression getRight() {
        return mRight;
    }

    @Override
    public void setOperands(Expression l, Expression r) {
        mLeft = l;
        mRight = r;
    }

    @Override
    public BinaryOperationType getOperationType() {
        return mType;
    }

    @Override
    public int getPriority() {
        return mPriority;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryOperation(this);
    }

    @Override
    public String toString() {
        return (String) accept(ToStringVisitor.INSTANCE);
    }
}
