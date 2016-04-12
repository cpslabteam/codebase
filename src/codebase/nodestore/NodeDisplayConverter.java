package codebase.nodestore;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import codebase.io.converters.Converter;
import codebase.io.converters.display.IntegerDisplayConverter;
import codebase.io.converters.display.StringLiteralConverter;

/**
 * A display {@link Converter} for {@link Node} objects.
 * <p>
 * Objects are written in the following format.
 * <ol>
 * <li>#node_num = I(node_type)</li>
 * <li>#node_num = R(rel_type, node_num, node_num)</li>
 * <li>#node_num = A(node_num, attr_name, value)</li>
 * </ol>
 */
public class NodeDisplayConverter
        implements Converter {
   
    private final IntegerDisplayConverter intConverter = new IntegerDisplayConverter();
    private final StringLiteralConverter stringConverter = new StringLiteralConverter();

    private void checkAndSkip(DataInput input, char expected) throws IOException {
        final char ch = (char) input.readByte();
        if (ch != expected) {
            throw new IOException("Malformed line: found '" + ch
                                  + "' while expecting '" + expected + "'");
        }
    }

    private void checkAndSkip(DataInput input, String expected) throws IOException {
        for (int i = 0; i < expected.length(); i++) {
            final char ch = (char) input.readByte();

            if (ch != expected.charAt(i)) {
                throw new IOException("Malformed line: found '" + ch
                                      + "' while expecting of '" + expected
                                      + "'");
            }  
        }
    }

    @Override
    public Object read(DataInput input) throws IOException {
        skipUntilChar(input, '#');
        final int nodeNumber = (Integer) intConverter.read(input);

        checkAndSkip(input, "= ");
        final int nodeType = input.readByte();

        checkAndSkip(input, '(');
        final Node node;
        if (nodeType == 'A') {
            final int instanceNode = (Integer) intConverter.read(input);
            final String attrName = (String) stringConverter.read(input);
            final String attrValue = (String) stringConverter.read(input);
            node = new AttributeNode(nodeNumber, instanceNode, attrName,
                    attrValue);
        } else if (nodeType == 'I') {
            final String instanceTypeName = (String) stringConverter
                    .read(input);
            node = new InstanceNode(nodeNumber, instanceTypeName);
        } else if (nodeType == 'R') {
            final String instanceTypeName = (String) stringConverter
                    .read(input);
            final int sourceNode = (Integer) intConverter.read(input);
            final int targetNode = (Integer) intConverter.read(input);
            node = new RelationNode(nodeNumber, instanceTypeName, sourceNode,
                    targetNode);
        } else {
            throw new IOException("Malformed line for node #" + nodeNumber
                                  + ": found '" + nodeType
                                  + "' while expecting one of 'A', 'I' or 'R'");
        }
        /*
         * ')' should was consumed in the process.
         */
        return node;
    }

    /**
     * Read the input characters until a specified char is seen.
     * <p>
     * The test character if found is consumed as well
     * 
     * @param input a {@link DataInput} object
     * @param testChar the character to be tested
     * @throws IOException if an error occurs while reading the stream
     */
    private void skipUntilChar(DataInput input, char testChar) throws IOException {
        while (true) {
            /*
             * Either the char is found or the 
             * loop is interrupted with an EOFException
             */
            if (input.readByte() == testChar)
                break;
        }
    }

    @Override
    public void write(DataOutput dataOutput, Object object) throws IOException {
        assert dataOutput != null;
        assert object instanceof Node;

        final String nodeRepresentation;
        if (object instanceof AttributeNode) {
            final AttributeNode node = (AttributeNode) object;
            nodeRepresentation = "#" + node.getNumber() + " = A("
                                 + node.getInstanceNode() + ", "
                                 + codebase.StringUtil.stringify(node.getName())
                                 + ", "
                                 + codebase.StringUtil.stringify(node.getValue())
                                 + ")";
        } else if (object instanceof InstanceNode) {
            final InstanceNode node = (InstanceNode) object;
            nodeRepresentation = "#"
                                 + node.getNumber()
                                 + " = I("
                                 + codebase.StringUtil.stringify(node
                                         .getTypeName()) + ")";
        } else if (object instanceof RelationNode) {
            final RelationNode node = (RelationNode) object;
            nodeRepresentation = "#"
                                 + node.getNumber()
                                 + " = R("
                                 + codebase.StringUtil.stringify(node
                                         .getTypeName()) + ", "
                                 + node.getSource() + ", " + node.getTarget()
                                 + ")";
        } else {
            throw new IllegalArgumentException(
                    "The supplied node object cannot be written because it is of an unknow type");
        }

        dataOutput.write(nodeRepresentation.getBytes());
        dataOutput.writeByte('\n');
    }
}
