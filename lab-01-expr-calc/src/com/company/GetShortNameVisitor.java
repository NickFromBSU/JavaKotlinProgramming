package com.company;

public class GetShortNameVisitor implements ExpressionVisitor {
    public static final GetShortNameVisitor INSTANCE = new GetShortNameVisitor();

    private GetShortNameVisitor() {}

    @Override
    public String visitBinaryOperation(BinaryOperation expr) {
        return switch (expr.getOperationType()) {
            case ADD -> "+";
            case SUB -> "-";
            case MUL -> "*";
            case DIV -> ":";
        };
    }

    @Override
    public String visitLiteral(Literal expr) {
        return Double.toString(expr.getValue());
    }

    @Override
    public String visitVariable(Variable expr) {
        if (expr.getName().length() <= 1) {
            return expr.getName();
        }
        return expr.getName().substring(0,1).concat("..");    }

    @Override
    public Object visitParenthesis(Parenthesis expr) {
        return "( )";
    }
}
