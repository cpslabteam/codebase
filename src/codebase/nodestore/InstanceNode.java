

package codebase.nodestore;

/**    
 * Node that represents an instance.
 */
public class InstanceNode extends
        Node {
    private final String typeName;

    public InstanceNode(int nodeNumber, String typeName) {
        super(nodeNumber);
        this.typeName = typeName;
    }
    
    
    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "InstanceNode [nodeType=" + typeName + "]";
    }
}
