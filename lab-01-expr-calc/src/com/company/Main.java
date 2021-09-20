package com.company;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter expression: ");
        Scanner scanner = new Scanner(System.in);
        Expression expr = null;
        for (boolean repeat = true; repeat; ) {
            repeat = false;
            String input = scanner.nextLine();
            try {
                expr = ParserImpl.INSTANCE.parse(input);
            } catch (Exception exception) {
                System.out.print(exception + "\nTry again.\n");
                repeat = true;
            }
        }
        String debug = (String) expr.accept(DebugRepresentationVisitor.INSTANCE);
        System.out.print("Debug representation: " + debug + '\n');
        Integer height = (Integer) expr.accept(new GetHeightVisitor(null));
        System.out.print("Expr-tree height: " + height + '\n');
        var variablesValues = (HashMap<String, Double>) expr.accept(new RefreshVariablesVisitor());
        Double result = (Double) expr.accept(new ComputeExpressionVisitor(variablesValues));
        System.out.print("Result: " + result + "\n");
        System.out.print("String restored from Expression: " + expr.toString() + '\n');

        // Beta feature :)
        TreeDrawer treeDrawer = new TreeDrawer();
        System.out.print(
                "\nNice representation for a tree, which was rather hard to implement:  :D\n\n");
        treeDrawer.drawTree(expr);
    }
}
