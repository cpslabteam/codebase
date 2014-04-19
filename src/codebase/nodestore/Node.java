package codebase.nodestore;

/**
 * The base class for nodes.
 */
public class Node {
    private final int nodeNumber;

    public Node(int num) {
        this.nodeNumber = num;
    }

    public int getNumber() {
        return nodeNumber;
    }

    @Override
    public int hashCode() {
        return nodeNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (nodeNumber != other.nodeNumber)
            return false;
        return true;
    }
}
