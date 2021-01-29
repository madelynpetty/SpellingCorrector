package spell;

public class Node implements INode {
    protected Node[] children;
    protected int value = 0;
    protected int ind = 0;

    public Node() {
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
        return children;
    }
}
