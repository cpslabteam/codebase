/*
 * Created on 1/Nov/2004
 */
package codebase.util;

import codebase.util.binary.Binary;

/**
 * Mathematical utility functions.
 * <p>
 * Computing the signal function for doubles is not completely straightforward.
 * For this reason we added the function for computing the <a
 * href="http://mathworld.wolfram.com/Sign.html">sgn</a> function for doubles.
 * For consistency the function for computing the signal for other primitive
 * types were added as well.
 */
public final class Math {
    
    /**
     * Powers of ten as long values 10^0; 10^1; 10^2 ...10^18.
     */
    static final long[] POWER10 = new long[] { 1L, 10L, 100L, 1000L, 10000L,
            100000L, 1000000L, 10000000L, 100000000L, 1000000000L,
            10000000000L, 100000000000L, 1000000000000L, 10000000000000L,
            100000000000000L, 1000000000000000L, 10000000000000000L,
            100000000000000000L, 1000000000000000000L };
    /**
     * The closest log2 per byte.
     */
    public static final byte[]  LOG2_PER_BYTE             = { 0, 0, 1, 1, 2, 2, 2, 2, 3,
            3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7        };
    
    /**
     * Prevent from instantiating this class.
     */
    private Math() {
    }
    
    /**
     * Computes the logarithm on a given base.
     * 
     * @param base the base
     * @param x the parameter
     * @return ln(x)/ln(b)
     */
    public static double log(final double x, final double base) {
        return java.lang.Math.log(x) / java.lang.Math.log(base);
    }
    
    /**
     * Computes that maximum of a list of integers.
     * 
     * @param values the list of integer values
     * @return the greates of all integers
     */
    public static int max(final int[] values) {
        int m = Integer.MIN_VALUE;
        for (int i : values) {
            if (i > m) {
                m = i;
            }
        }
        return m;
    }
    
    /**
     * Rounds a double to have predefined number of decimal places.
     * 
     * @param value the value to be rounded
     * @param decimalPlaces the number of decimals
     * @return <code>round(value*(10^decimals))/(10^decimals)</code>.
     */
    public static double round(final double value, final int decimalPlaces) {
        final double baseTen = 10.0;
        double powerOfTen = 1;
        int decimals = decimalPlaces;
        while (decimals > 0) {
            powerOfTen *= baseTen;
            decimals -= 1;
        }
        return java.lang.Math.round(value * powerOfTen) / powerOfTen;
    }
          
    
    /**
     * A very efficient function to compute powers of 10.
     * <p>
     * Since a long is a signed 64 bit two's-complement number, its range is
     * <code>-9,223,372,036,854,775,808</code> to
     * <code>9,223,372,036,854,775,807</code>. Thus,
     * <code>10^18 < 9,223,372,036,854,775,807 < 10^19</code>.
     * <p>
     * <b>Precondition</b>: <code>0 <= exponent <= 18 </code>
     * 
     * @param exponent the exponent of the base. Cannot exceed 18.
     * @return the value <code>10^exponent</code>
     */
    public static long pow10(final int exponent) {
        return POWER10[exponent];
    }
    
    /**
     * Computes the integer, base 2, logarithm of a long value.
     * 
     * @param l the long
     * @return an the exponent <i>e</i> such that
     *         <code>2**<i>e</i> &leq& <i>l</i></code>, or zero if <i>l</i>
     *         is zero.
     */
    public static int log2(final long l) {
        int log = 56;
        int lg2 = LOG2_PER_BYTE[(int) ((l >>> log) & Binary.LONG_BYTE_MASK)];
        while (lg2 == 0 && log > 0) {
            log -= Binary.BITS_PER_BYTE;
            lg2 = LOG2_PER_BYTE[(int) ((l >>> log) & Binary.LONG_BYTE_MASK)];
        }
        return log + lg2;
    }
    
    /**
     * Computes the integer, base 2, logarithm of a integer value.
     * 
     * @param i the integer
     * @return an the exponent <i>e</i> such that
     *         <code>2**<i>e</i> &leq& <i>l</i></code>, or zero if <i>i</i>
     *         is zero.
     */
    public static int log2(final int i) {
        int log = 24;
        int lg2 = LOG2_PER_BYTE[(i >>> log) & Binary.INT_LOW_BYTE_MASK];
        while (lg2 == 0 && log > 0) {
            log -= Binary.BITS_PER_BYTE;
            lg2 = LOG2_PER_BYTE[(i >>> log) & Binary.INT_LOW_BYTE_MASK];
        }
        return log + lg2;
    }
    
    /**
     * Computes the hash code of a double value.
     * 
     * @param value the value to be hashed
     * @return the hash code
     */
    public static int hash(final double value) {
        final long bits = Double.doubleToLongBits(value);
        return (int) (bits ^ (bits >>> Binary.INT_LENGTH_BITS));
    }
    
    /**
     * Checks if two doubles are the same.
     * 
     * @param x first value
     * @param y second value
     * @return <code>true</code> if the values are equal or both are
     *         <code>NaN</code>
     */
    public static boolean equals(final double x, final double y) {
        return ((Double.isNaN(x) && Double.isNaN(y)) || x == y);
    }
    
    /**
     * Generates a random integer using the Java random number generator.
     * 
     * @param max the upper limit for the random value
     * @return a random integer <i>i</i> value such that
     *         <code>0 < <i>i</i> < max</code>
     */
    public static int randomInt(final int max) {
        return (int) java.lang.Math.floor(java.lang.Math.random() * max);
    }
    
    /**
     * Computes the signal of a double.
     * 
     * @param x the double
     * @return returns <code>+1.0</code> if <code>x > 0</code>,
     *         <code>0.0</code> if <code>x = 0.0</code>, and
     *         <code>-1.0</code> if <code>x < 0</code>. Returns
     *         <code>NaN</code> if <code>x</code> is <code>NaN</code>.
     */
    public static double sign(final double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        if (x == 0.0) {
            return 0.0;
        } else {
            if (x > 0.0) {
                return 1.0;
            } else {
                return -1.0;
            }
        }
    }
    
    /**
     * Computes the signal of a float.
     * 
     * @param x the float
     * @return returns <code>+1.0F</code> if <code>x > 0</code>,
     *         <code>0.0F</code> if <code>x = 0.0F</code>, and
     *         <code>-1.0F</code> if <code>x < 0</code>. Returns
     *         <code>NaN</code> if <code>x</code> is <code>NaN</code>.
     */
    public static float sign(final float x) {
        if (Float.isNaN(x)) {
            return Float.NaN;
        }
        if (x == 0.0F) {
            return 0.0F;
        } else {
            if (x > 0.0F) {
                return 1.0F;
            } else {
                return -1.0F;
            }
        }
    }
    
    /**
     * Computes the signal of a byte.
     * 
     * @param x the byte
     * @return returns <code>(byte)+1</code> if <code>x > 0</code>,
     *         <code>(byte) 0</code> if <code>x = 0</code>, and
     *         <code>(byte)-1</code> if <code>x < 0</code>.
     */
    public static byte sign(final byte x) {
        if (x == (byte) 0) {
            return (byte) 0;
        } else {
            if (x > (byte) 0) {
                return (byte) 1;
            } else {
                return (byte) -1;
            }
        }
    }
    
    /**
     * Computes the signal of an integer.
     * 
     * @param x the byte
     * @return returns <code>+1</code> if <code>x > 0</code>,
     *         <code> 0</code> if <code>x = 0</code>, and <code>-1</code>
     *         if <code>x < 0</code>.
     */
    public static byte sign(final int x) {
        if (x == 0) {
            return 0;
        } else {
            if (x > 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    /**
     * Computes the signal of a long.
     * 
     * @param x the byte
     * @return returns <code>+1L</code> if <code>x > 0</code>,
     *         <code>0L</code> if <code>x = 0</code>, and <code>-1L</code>
     *         if <code>x < 0</code>.
     */
    public static long sign(final long x) {
        if (x == 0L) {
            return 0L;
        } else {
            if (x > 0L) {
                return 1L;
            } else {
                return -1L;
            }
        }
    }
    
    /**
     * Computes the sum of an array of integers.
     * 
     * @param values the array to be summed, cannot be <code>null</code>
     * @return the sum of all values in the array
     */
    public static int sum(final int[] values) {
        int t = 0;
        for (int i : values) {
            t += i;
        }
        return t;
    }
}
