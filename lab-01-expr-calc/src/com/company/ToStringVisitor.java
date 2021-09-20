package com.company;

public class ToStringVisitor implements ExpressionVisitor {
    public static ToStringVisitor INSTANCE = new ToStringVisitor();

    private ToStringVisitor() {}

    @Override
    public String visitBinaryOperation(BinaryOperation operation) {
        return operation.getLeft().accept(new ToStringVisitor()) + " " +
                operation.accept(GetShortNameVisitor.INSTANCE) + " " +
                operation.getRight().accept(new ToStringVisitor());
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
    public Object visitParenthesis(Parenthesis expr) {
        return "(" + expr.getOperand().accept(new ToStringVisitor()) + ")";
    }
}
