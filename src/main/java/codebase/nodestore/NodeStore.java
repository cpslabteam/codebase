package codebase.nodestore;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.util.HashMap;

/**
 * A node store.
 */
public class NodeStore {

    /**
     * Maintains the nodes by number.
     */
    private final HashMap<Integer, Node> nodesByNumber = new HashMap<Integer, Node>(1024);

    public int read(final ObjectInput input, final int numObjects)
            throws IOException, InvalidObjectException {
        int i = 0;
        while (i < numObjects && input.available() > 0) {
            final Object o;
            try {
                o = input.readObject();
                if (!(o instanceof Node)) {
                    throw new InvalidObjectException(
                            "Object " + o.getClass().getCanonicalName() + " is not a node.");
                }
                insert((Node) o);
            } catch (ClassNotFoundException e) {
                throw new InvalidObjectException(e.getMessage());
            }
            i++;
        }
        return i;
    }

    public void insert(final Node node) {
        nodesByNumber.put(node.getNumber(), node);
    }

    public void delete(final Node node) {
        nodesByNumber.remove(node.getNumber());
    }

    public void update(final Node node) {
        nodesByNumber.put(node.getNumber(), node);
    }

    public Node query(final int nodeNumber) {
        return nodesByNumber.get(nodeNumber);
    }
}
