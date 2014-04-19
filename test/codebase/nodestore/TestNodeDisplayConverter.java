package codebase.nodestore;

import java.io.IOException;

import junit.framework.TestCase;
import codebase.streams.ByteArrayDataInput;
import codebase.streams.ByteArrayDataOutput;

public class TestNodeDisplayConverter extends
        TestCase {

    /**
     * Tests that an attribute node is successfully read and written.
     */
    public void testAtributeNodeSimple() throws IOException {
        AttributeNode attrNode = new AttributeNode(1, 123, "a field name", "some value");
        NodeDisplayConverter c = new NodeDisplayConverter();

        byte[] buffer = new byte[255];
        ByteArrayDataOutput output = new ByteArrayDataOutput(buffer);
        c.write(output, attrNode);

        ByteArrayDataInput input = new ByteArrayDataInput(buffer);
        AttributeNode node = (AttributeNode) c.read(input);

        assertEquals(1, node.getNumber());
        assertEquals(123, node.getInstanceNode());
        assertEquals("a field name", node.getName());
        assertEquals("some value", node.getValue());
    }

    /**
     * Tests that an instance node is successfully read and written.
     */
    public void testInstanceNodeSimple() throws IOException {
        InstanceNode instNode = new InstanceNode(123, "an instance type");
        NodeDisplayConverter c = new NodeDisplayConverter();

        byte[] buffer = new byte[255];
        ByteArrayDataOutput output = new ByteArrayDataOutput(buffer);
        c.write(output, instNode);

        ByteArrayDataInput input = new ByteArrayDataInput(buffer);
        InstanceNode node = (InstanceNode) c.read(input);

        assertEquals(123, node.getNumber());
        assertEquals("an instance type", node.getTypeName());
    }

    /**
     * Tests that a relation node is successfully read and written.
     */
    public void testRelationNodeSimple() throws IOException {
        RelationNode relNode = new RelationNode(123, "a relation type", 456, 789);
        NodeDisplayConverter c = new NodeDisplayConverter();

        byte[] buffer = new byte[255];
        ByteArrayDataOutput output = new ByteArrayDataOutput(buffer);
        c.write(output, relNode);

        ByteArrayDataInput input = new ByteArrayDataInput(buffer);
        RelationNode node = (RelationNode) c.read(input);

        assertEquals(123, node.getNumber());
        assertEquals("a relation type", node.getTypeName());
        assertEquals(456, node.getSource());
        assertEquals(789, node.getTarget());
    }
}
