package codebase.nodestore;

/**
 * An instance of a relationship between nodes.
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((relationName == null) ? 0 : relationName.hashCode());
        result = prime * result + source;
        result = prime * result + target;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        RelationNode other = (RelationNode) obj;
        if (relationName == null) {
            if (other.relationName != null)
                return false;
        } else if (!relationName.equals(other.relationName))
            return false;
        if (source != other.source)
            return false;
        if (target != other.target)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RelationNode [relationName=" + relationName + ", source=" + source + ", target="
                + target + "]";
    }
}
