package codebase.streams;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import shared.properties.api.IProperty;
import shared.properties.base.DefaultPropertyValue;
import shared.properties.base.Property;
import shared.properties.base.datatypes.NumberDataType;

/**
 * An output stream that waits for a predefined interval before writing <b>each byte</b>
 * into another stream.
 * <p>
 * Used primarily for testing the timeout behavior of other classes.
 */
public class DelayedOutputStream extends
        FilterOutputStream {

    /**
     * The the number of bytes per second.
     */
    private final IProperty intervalProperty;

    /**
     * Constructs an output stream that converges a predefined bandwidth.
     * 
     * @param interval the time to wait before sending the message in milliseconds.
     * @param output The output stream to be wrapped.
     * @throws IllegalArgumentException if the bandwidth is not positive
     */
    public DelayedOutputStream(final OutputStream output, final int interval) {
        super(output);
        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be positive");
        }
        this.intervalProperty =
            new Property.PropertyBuilder("Interval", NumberDataType.getInstance()).setCloneable(true).setName("Stream Interval")
                    .setReadOnly(false).setTransient(true).setPropertyValue(new DefaultPropertyValue(interval))
                    .setDescription("Interrval between messages of this InputStream in millisenconds.").build();
    }

    /**
     * Constructs an output stream that converges a predefined bandwidth.
     * 
     * @param intervalProperty the time to wait before sending the message in
     *            milliseconds. Must be a {@link NumberPropertyType}. This stream will be
     *            attached to this property and each update in the property's value will
     *            instantly affect the stream
     * @param input The output stream to be wrapped.
     * @throws IllegalArgumentException if the bandwidth is not positive
     */
    public DelayedOutputStream(final OutputStream output, final IProperty intervalProperty) {
        super(output);
        if (!intervalProperty.getPropertyType().equals(NumberDataType.getInstance())) {
            throw new IllegalArgumentException("The property must be a " + NumberDataType.class.getSimpleName() + ".");
        }
        if ((Integer) intervalProperty.getValue() <= 0) {
            throw new IllegalArgumentException("The interval must be positive");
        }
        this.intervalProperty = intervalProperty;
    }


    /**
     * Writes a byte waiting for a predefined amount of time writting.
     * 
     * @param b the byte to be written.
     * @throws IOException when if writing on the decorated stream fails or if the the
     *             stream was not allowed to wait the specified amount of milliseconds
     *             before writing the byte
     */
    @Override
    public void write(int b) throws IOException {
        try {
            Thread.sleep((Integer) intervalProperty.getValue());
        } catch (InterruptedException e) {
            throw new TimeoutException(e);
        }
        super.write(b);
    }

    /**
     * Writes a byte waiting for a predefined amount of time writting.
     * 
     * @param b the byte to be written.
     * @throws IOException when if writing on the decorated stream fails or if the the
     *             stream was not allowed to wait the specified amount of milliseconds
     *             before writing the byte
     */
    @Override
    public void write(byte[] b) throws IOException {
        try {
            Thread.sleep((Integer) intervalProperty.getValue());
        } catch (InterruptedException e) {
            throw new TimeoutException(e);
        }
        super.write(b);
    }

    /**
     * Writes a byte waiting for a predefined amount of time writting.
     * 
     * @param b the byte to be written.
     * @param off the byte offset
     * @param len the byte len to write
     * @throws IOException when if writing on the decorated stream fails or if the the
     *             stream was not allowed to wait the specified amount of milliseconds
     *             before writing the byte
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        final byte[] segment = new byte[len];
        System.arraycopy(b, off, segment, 0, len);
        this.write(segment);
    }
}
