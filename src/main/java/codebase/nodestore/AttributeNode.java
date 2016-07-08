package codebase.nodestore;

/**
 * An attribute node.
 */
public class AttributeNode extends Node {

    private final int instanceNode;
    private final String name;
    private final String value;

    public AttributeNode(int nodeNumber, int instanceNumber, String name, String value) {
        super(nodeNumber);
        this.instanceNode = instanceNumber;
        this.name = name;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getInstanceNode() {
        return instanceNode;
    }


    /**
     * @return a string representation of an attribute node for debugging purposes.
     */
    @Override
    public String toString() {
        return "AttributeNode [instanceNode=" + instanceNode + ", name=" + name + ", value=" + value
                + "]";
    }
}
