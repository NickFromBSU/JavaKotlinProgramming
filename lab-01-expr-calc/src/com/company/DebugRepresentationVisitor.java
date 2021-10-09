package com.company;

public class DebugRepresentationVisitor implements ExpressionVisitor {
    public static final DebugRepresentationVisitor INSTANCE = new DebugRepresentationVisitor();

    private DebugRepresentationVisitor() {}

    @Override
    public String visitBinaryOperation(BinaryOperation operation) {
        String left = (String) operation.getLeft().accept(this);
        String right = (String) operation.getRight().accept(this);
        return operation.getOperationType().toString() + "(" + left +", " + right + ")";
    }

    @Override
    public String visitLiteral(Literal expr) {
        return Double.toString(expr.getValue());
    }

    @Override
    public String visitVariable(Variable expr) {
        return expr.getName();
    }

    @Override
    public String visitParenthesis(Parenthesis expr) {
        return "paren(" + expr.getOperand().accept(this) + ")";
    }
}
