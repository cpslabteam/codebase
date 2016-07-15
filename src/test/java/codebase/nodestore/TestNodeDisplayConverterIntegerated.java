package codebase.nodestore;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

import junit.framework.TestCase;
import codebase.StringUtil;
import codebase.io.converters.ObjectConverterInput;
import codebase.io.converters.ObjectConverterOutput;
import codebase.streams.ByteArrayDataOutput;
import codebase.streams.StringInputStream;

public class TestNodeDisplayConverterIntegerated extends
        TestCase {

    public static final String nodeStore = "#1 = I(\"type1\")\n" + "#2 = I(\"type2\")\n"
            + "#3 = R(\"child\", 1, 2)\n";

    public void testNodeInput() throws IOException, ClassNotFoundException {
        // Create a node input
        DataInputStream inputStream = new DataInputStream(new StringInputStream(nodeStore));
        ObjectConverterInput nodeInput = new ObjectConverterInput(inputStream,
                new NodeDisplayConverter());

        // create a node output
        byte[] buffer = new byte[255];
        DataOutput output = new ByteArrayDataOutput(buffer);
        ObjectConverterOutput nodeOutput = new ObjectConverterOutput(output,
                new NodeDisplayConverter());

        // read objects from the input and write then on the output
        Object node1 = nodeInput.readObject();
        nodeOutput.writeObject(node1);

        Object node2 = nodeInput.readObject();
        nodeOutput.writeObject(node2);

        Object node3 = nodeInput.readObject();
        nodeOutput.writeObject(node3);

        final int bufferTrimLength = nodeStore.length();
        String result = new String(buffer, 0, bufferTrimLength, StringUtil.UTF8);

        assertEquals(nodeStore, result);
    }
}
