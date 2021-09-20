package com.company;

import java.util.HashMap;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    private final HashMap<String, Double> mVariables;

    ComputeExpressionVisitor(HashMap<String, Double> variables) {
        mVariables = variables;
    }

    @Override
    public Object visitBinaryOperation(BinaryOperation operation) {
        Double left_result = (Double) operation.getLeft().accept(this);
        Double right_result = (Double) operation.getRight().accept(this);
        return switch (operation.getOperationType()) {
            case ADD -> left_result + right_result;
            case MUL -> left_result * right_result;
            case DIV -> left_result / right_result;
            case SUB -> left_result - right_result;
        };
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitVariable(Variable expr) {
        return mVariables.get(expr.getName());
    }

    @Override
    public Object visitParenthesis(Parenthesis expr) {
        return expr.getOperand().accept(this);
    }
}
