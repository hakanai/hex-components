/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.interpreters.primitives;

/**
 * Holds a mask and shift count, useful for computing fields which are at specific
 * bit offsets inside a larger value.
 *
 * @author trejkaz
 */
public class LongBitField {
    private final int shift;
    private final int nextShift;
    private final long mask;

    private LongBitField(int shift, int nextShift, long mask) {
        this.shift = shift;
        this.nextShift = nextShift;
        this.mask = mask;
    }

    //TODO: Consider checking whether shift&mask exceeds the precision of the number passed in.

    public long evaluate(long value) {
        return (value >> shift) & mask;
    }

    /**
     * Creates a bit field which contains the first {@code numBits} bits of a short value.
     * Bit fields for the subsequent fields can be retrieved by calling {@link #next(int)}
     * on the initial bit field.
     *
     * @param numBits the number of bits.
     * @return the bit field.
     * @see #next(int)
     */
    public static LongBitField lowest(int numBits) {
        int shift = 0;
        long mask = (1L << numBits) - 1;
        return new LongBitField(shift, numBits, mask);
    }

    /**
     * Creates a bit field which contains the next {@code numBits} above the bits
     * contained by this one.
     *
     * @param numBits the number of bits.
     * @return the bit field.
     */
    public LongBitField next(int numBits) {
        int shift = this.nextShift;
        long mask = (1L << numBits) - 1;
        return new LongBitField(shift, shift + numBits, mask);
    }
}
