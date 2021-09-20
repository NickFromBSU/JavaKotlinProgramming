package com.company;

import java.util.HashMap;
import java.util.Scanner;

public class RefreshVariablesVisitor implements ExpressionVisitor {
    private final HashMap<String, Double> mVariables;

    RefreshVariablesVisitor() {
        mVariables = new HashMap<>();
    }

    @Override
    public HashMap<String, Double> visitBinaryOperation(BinaryOperation operation) {
        operation.getLeft().accept(this);
        operation.getRight().accept(this);
        return mVariables;
    }

    @Override
    public HashMap<String, Double> visitLiteral(Literal expr) {
        return mVariables;
    }

    @Override
    public HashMap<String, Double> visitVariable(Variable expr) {
        if (mVariables.containsKey(expr.getName())) {
            return mVariables;
        }
        Scanner scanner = new Scanner(System.in);
        double value = 0;
        boolean repeat = true;
        while (repeat) {
            repeat = false;
            System.out.print("Enter the value for " + expr.getName() + '\n');
            String input = scanner.nextLine();
            try {
                value = Double.parseDouble(input);
            } catch (Exception exception) {
                System.out.print("Invalid input. Try again.\n");
                repeat = true;
            }
        }
        mVariables.put(expr.getName(), value);
        return mVariables;
    }

    @Override
    public HashMap<String, Double> visitParenthesis(Parenthesis expr) {
        expr.getOperand().accept(this);
        return mVariables;
    }
}
