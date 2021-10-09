package com.company;

import java.util.ArrayList;
import java.util.HashMap;

class NodePos {
    NodePos(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public int x;
    public int y;
    public String name;
}

public class TreeDrawer {
    private ArrayList<NodePos> mNodesCoordinates;
    private HashMap<Expression, Integer> mSubtreeToHeight;

    public void drawTree(Expression tree) {
        mSubtreeToHeight = new HashMap<>();
        mNodesCoordinates =  new ArrayList<>();
        var visitor = new GetHeightVisitor(mSubtreeToHeight);
        tree.accept(visitor);

        int min_x = FillArrayWithCoordinates(tree, 0, 0);
        int offset = min_x < 0 ? -min_x : 0;
        mNodesCoordinates.sort((NodePos a, NodePos b) -> {
            if (a.y != b.y) {
                return a.y < b.y ? -1 : 1;
            }
            return a.x <= b.x ? -1 : 1;
        });

        Print(offset);
    }

    private void Print(int offset) {
        int write_x = 0;
        int write_y = 0;
        StringBuilder output = new StringBuilder();
        for (NodePos token : mNodesCoordinates) {
            int x = token.x + offset;
            int y = token.y;
            while (write_y < y) {
                write_y++;
                write_x = 0;
                output.append('\n');
            }
            while (write_x < x) {
                write_x++;
                output.append(' ');
            }
            output.append(token.name);
            write_x += token.name.length();
        }
        System.out.print(output);
    }

    // A recursion. Fills |nodes_coordinates| array with proper
    // coordinates to print nodes of a tree. Returns min x-coordinate of a tree.
    private int FillArrayWithCoordinates(Expression node, int x, int y) {
        mNodesCoordinates.add(new NodePos(x, y,
                (String) node.accept(GetShortNameVisitor.INSTANCE)));
        if (!(node instanceof Operation)) {
            return x;
        }
        if (node instanceof Parenthesis) {
            mNodesCoordinates.add(new NodePos(x + 1, y + 1, "|"));
            return FillArrayWithCoordinates(((Parenthesis)node).getOperand(),
                    x + 1, y + 2);
        }
        BinaryOperation operation = (BinaryOperation) node;
        int left_expr_x = x - 4 * mSubtreeToHeight.get(node) + 4;
        int right_expr_x = x + 4 * mSubtreeToHeight.get(node) - 4;
        mNodesCoordinates.add(new NodePos(left_expr_x + 1, y + 1,"/"));
        mNodesCoordinates.add(new NodePos(right_expr_x, y + 1,"\\"));
        FillArrayWithCoordinates(operation.getRight(), right_expr_x, y + 2);
        return FillArrayWithCoordinates(operation.getLeft(), left_expr_x, y + 2);
    }
}
