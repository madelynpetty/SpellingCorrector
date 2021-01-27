package spell;

public class Node implements INode {
    public char c;
    public Node[] children;
    public int value = 0;
    int i = 0;

    public Node(char c) {
        this.c = c;
        children = new Node[26];
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public INode[] getChildren() {
//        return new INode[0];
        return children;
    }
}
