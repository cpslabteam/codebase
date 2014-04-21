package codebase.nodestore;

/**
 * 
 */
public class RelationNode extends Node {
    private final String relationName;
    private final int source;
    private final int target;

    public RelationNode(int nodeNumber, String relationName, int source, int target) {
        super(nodeNumber);
        this.relationName = relationName;
        this.source = source;
        this.target = target;
    }
    
    public String getTypeName() {
        return relationName;
    }
    
    public int getSource() {
        return source;
    }
    
    public int getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "RelationNode [relationName=" + relationName + ", source=" + source + ", target="
                + target + "]";
    }
}
