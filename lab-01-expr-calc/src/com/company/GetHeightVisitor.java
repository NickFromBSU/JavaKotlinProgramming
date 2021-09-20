package com.company;

import java.util.HashMap;

public class GetHeightVisitor implements ExpressionVisitor {
    private final HashMap<Expression, Integer> mNodeToHeight;

    GetHeightVisitor(HashMap<Expression, Integer> heightsOfSubtrees) {
        mNodeToHeight = heightsOfSubtrees;
        if (mNodeToHeight != null) {
            mNodeToHeight.clear();
        }
    }

    @Override
    public Integer visitBinaryOperation(BinaryOperation operation) {
        int height = 1 + Math.max((Integer) operation.getLeft().accept(this),
                (Integer) operation.getRight().accept(this));
        if (mNodeToHeight != null) {
            mNodeToHeight.put(operation, height);
        }
        return height;
    }

    @Override
    public Integer visitLiteral(Literal expr) {
        if (mNodeToHeight != null) {
            mNodeToHeight.put(expr, 1);
        }
        return 1;
    }

    @Override
    public Integer visitVariable(Variable expr) {
        if (mNodeToHeight != null) {
            mNodeToHeight.put(expr, 1);
        }
        return 1;
    }

    @Override
    public Integer visitParenthesis(Parenthesis expr) {
        int height = 1 + (Integer) ((Parenthesis)expr).getOperand().accept(this);
        if (mNodeToHeight != null) {
            mNodeToHeight.put(expr, height);
        }
        return height;
    }
}
