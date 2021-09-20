package com.company;

import java.util.ArrayList;
import java.util.Stack;

public class ParserImpl implements Parser {
    public static ParserImpl INSTANCE = new ParserImpl();

    private ParserImpl() {}

    private static boolean isLetter(char x) {
        return ('A' <= x && x <= 'Z') || ('a' <= x && x <= 'z');
    }

    private static boolean isDigit(char x) {
        return ('0' <= x && x <= '9') || x == '.' || x == '-';
    }

    private static void addProperToken(char new_token, ArrayList<Expression> tokens) {
        switch (new_token) {
            case '+' -> tokens.add(new BinaryOperationImpl(BinaryOperationType.ADD, 1));
            case '-' -> tokens.add(new BinaryOperationImpl(BinaryOperationType.SUB, 1));
            case '*' -> tokens.add(new BinaryOperationImpl(BinaryOperationType.MUL, 2));
            case '/' -> tokens.add(new BinaryOperationImpl(BinaryOperationType.DIV, 2));
            case '(' -> tokens.add(new Bracket(true));
            case ')' -> tokens.add(new Bracket(false));
            case ' ' -> { }
            default -> throw new RuntimeException("Invalid input");
        }
    }

    private static ArrayList<Expression> tokenize(String input) {
        ArrayList<Expression> tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); ) {
            StringBuilder tokenStr = new StringBuilder();
            while (i < input.length() && isLetter(input.charAt(i))) {
                tokenStr.append(input.charAt(i));
                i++;
            }
            if (!tokenStr.isEmpty()) {
                tokens.add(new VariableImpl(tokenStr.toString()));
                continue;
            }
            while (i < input.length() && isDigit(input.charAt(i))) {
                tokenStr.append(input.charAt(i));
                i++;
            }
            if (tokenStr.toString().equals("-")) {
                i--;
            } else if (!tokenStr.isEmpty()) {
                try {
                    tokens.add(new LiteralImpl(Double.parseDouble(tokenStr.toString())));
                    continue;
                } catch (Exception exception) {
                    throw new RuntimeException("Invalid number");
                }
            }
            addProperToken(input.charAt(i), tokens);
            i++;
        }
        return tokens;
    }

    public Expression parse(String input) {
        Stack<Expression> operands = new Stack<>();
        Stack<Expression> operations = new Stack<>();

        for (Expression token : tokenize(input)) {
            if (token instanceof LiteralImpl || token instanceof VariableImpl) {
                operands.add(token);
            } else if (token instanceof Bracket) {
                if (((Bracket)token).isLeft()) {
                    operations.add(token);
                } else {
                    while (!operations.isEmpty() && !(operations.peek() instanceof Bracket
                                    && ((Bracket)operations.peek()).isLeft())) {
                        processLastWith((Operation)operations.pop(), operands);
                    }
                    if (operations.isEmpty()) {
                        throw new RuntimeException("Wrong brackets");
                    }
                    operations.pop();
                    processLastWith(new ParenthesisImpl(), operands);
                }
            } else {
                while (!operations.isEmpty() && operations.peek() instanceof Operation &&
                        ((Operation)operations.peek()).getPriority()
                                >= ((Operation)token).getPriority()) {
                    processLastWith((Operation)operations.pop(), operands);
                }
                operations.add(token);
            }
        }

        while (!operations.isEmpty()) {
            if (!(operations.peek() instanceof Operation)) {
                throw new RuntimeException("Invalid input");
            }
            processLastWith((Operation)operations.pop(), operands);
        }
        if (operands.size() != 1) {
            throw new RuntimeException("Invalid input");
        }
        return operands.peek();
    }

    private static void processLastWith(Operation operation, Stack<Expression> operands) {
        if (operands.size() == 0) {
            throw new RuntimeException("Invalid input");
        }
        if (operation instanceof Parenthesis) {
            ((Parenthesis)operation).setOperand(operands.pop());
        } else if (operands.size() < 2) {
            throw new RuntimeException("Invalid input");
        } else {
            Expression right_operand = operands.pop();
            ((BinaryOperation)operation).setOperands(operands.pop(), right_operand);
        }
        operands.add(operation);
    }
}
