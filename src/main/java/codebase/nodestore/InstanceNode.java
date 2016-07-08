package codebase.nodestore;

/**
 * Node that represents an instance.
 */
public class InstanceNode extends Node {
    private final String typeName;

    public InstanceNode(int nodeNumber, String typeName) {
        super(nodeNumber);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "InstanceNode [nodeType=" + typeName + "]";
    }
}
