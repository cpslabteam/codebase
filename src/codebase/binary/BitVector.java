package codebase.binary;

import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;

import codebase.Binary;


/**
 * Optimized implementation of a vector of bits.
 * <p>
 * This class is similar to {@link java.util.BitSet}, but also includes the a
 * {@link #cachedCount}achedCount() method, which efficiently computes the number of one
 * bits, optimized read and write operations, and a boolean {@link #get(int)} method.
 * 
 *@since Created on 11/Fev/200
 */
public final class BitVector {

    /**
     * The mask of a bit index. We use the tree lower bits of the integer index to get the
     * number of the bit to set.
     */
    static final int BIT_IDX_MASK = 0x0007;

    /**
     * Array of bytes that encode the bit array.
     */
    private byte[] bits;

    /**
     * Size of the array in bits.
     */
    private int size;

    /**
     * Number of set bits.
     */
    private int cachedCount = -1;

    /**
     * Number of rights shifts that are equivalent to an integer division by 8.
     */
    public static final int DIV_8_SHIFTS = 3;

    /**
     * Constructs a vector capable of holding n bits.
     * 
     * @param n size of the array in bits
     * @throws IllegalArgumentException if the number of bits its not at least 1
     */
    public BitVector(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("The size must be positive");
        }
        size = n;
        bits = new byte[(size >> BitVector.DIV_8_SHIFTS) + 1];
    }

    /**
     * Sets the value of bit to zero.
     * 
     * @param bitIndex the index of the bit to set
     */
    public void clear(final int bitIndex) {
        bits[bitIndex >> BitVector.DIV_8_SHIFTS] &= ~(1 << (bitIndex & BIT_IDX_MASK));
        cachedCount = -1;
    }

    /**
     * Returns the total number of set bits.
     * <p>
     * This is efficiently computed and cached, so that, if the vector is not changed, no
     * recomputation is done for repeated calls.
     * 
     * @return the number of one bits in this vector
     */
    public int count() {
        final int byteIndexMask = 0xFF;
        final boolean vectorWasModified = (cachedCount == -1);
        if (vectorWasModified) {
            int c = 0;
            int end = bits.length;
            for (int i = 0; i < end; i++) {
                /*
                 * sum bits per byte
                 */
                c += Binary.getSetBits(bits[i] & byteIndexMask);
                cachedCount = c;
            }
        }
        return cachedCount;
    }

    /**
     * Returns true if bit is one and false if it is zero.
     * 
     * @param bitIndex the index of the bit to set
     * @return the value of <code>bits[bitIndex]</code>
     */
    public boolean get(final int bitIndex) {
        return (bits[bitIndex >> BitVector.DIV_8_SHIFTS] & (1 << (bitIndex & BIT_IDX_MASK))) != 0;
    }

    /**
     * Constructs a bit vector from a data input.
     * 
     * @param input the data input to be used
     * @throws IOException if an error occurs while reading from the data input
     */
    public void read(final DataInput input) throws IOException {
        size = input.readInt(); // read size
        cachedCount = input.readInt(); // read cachedCount
        bits = new byte[(size >> BitVector.DIV_8_SHIFTS) + 1]; // allocate bits
        input.readFully(bits, 0, bits.length); // read bits
    }

    /**
     * Sets the value of bit to one.
     * 
     * @param bitIndex the index of the bit to set
     */
    public void set(final int bitIndex) {
        bits[bitIndex >> BitVector.DIV_8_SHIFTS] |= (1 << (bitIndex & BIT_IDX_MASK));
        cachedCount = -1;
    }

    /**
     * Returns the number of bits in this vector.
     * <p>
     * This is also one greater than the number of the largest valid bit number.
     * 
     * @return the size in bits
     */
    public int size() {
        return size;
    }

    /**
     * Writes this vector to a data output.
     * 
     * @param output the data output to write the vector
     * @throws IOException if an error occurs while writting to the stream
     */
    public void write(final DataOutput output) throws IOException {
        output.writeInt(size());
        output.writeInt(count());
        output.write(bits);
    }
}
