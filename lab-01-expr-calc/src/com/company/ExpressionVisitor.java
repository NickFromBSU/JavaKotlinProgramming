package com.company;

public interface ExpressionVisitor {
    Object visitBinaryOperation(BinaryOperation operation);
    Object visitLiteral(Literal expr);
    Object visitVariable(Variable expr);
    Object visitParenthesis(Parenthesis expr);
}
