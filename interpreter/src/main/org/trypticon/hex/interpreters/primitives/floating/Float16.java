/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2013  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.primitives.floating;

import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.interpreters.primitives.BitField;

import java.lang.*;

/**
 * IEEE 754 half-precision (16-bit) floating point.
 *
 * @author trejkaz
 */
public class Float16 extends Number implements Value {
    // Breakdown of the format.
    private static final BitField mantissaField = BitField.lowest(10);
    private static final BitField exponentField = mantissaField.next(5);
    private static final BitField signField = exponentField.next(1);

    private final short encodedValue;

    public Float16(short encodedValue) {
        this.encodedValue = encodedValue;
    }

    @Override
    public float floatValue() {
        // Essentially we are up-casting to float.
        int mantissa = mantissaField.evaluate(encodedValue);
        int exponent = exponentField.evaluate(encodedValue);
        int sign = signField.evaluate(encodedValue);

        if (exponent == 0x1f) {
            // NaN/Infinity case. It's OK to do this without filling the mantissa. Any non-zero in there means NaN.
            exponent = 0xff;
        } else if (exponent != 0) {
            // Normalised case. Re-bias the exponent and shift the mantissa to the right place.
            exponent += (127 - 15);
            mantissa <<= (23 - 10);
        } else if (mantissa != 0) {
            // Sub-normal case. Treat the exponent as one higher than it is.
            exponent = (127 - 15) + 1;
            mantissa <<= (23 - 10);
            // Then shift the bits upwards until the leading bit is 1 again.
            do {
                mantissa <<= 1;
                exponent--;
            } while ((mantissa & 0x800000) == 0);
            // Then discard the leading bit.
            mantissa &= 0x7fffff;
        } // else it's the +/- zero case, mantissa and exponent both zero, so nothing to do.

        // 1 bit of sign, 8 bits of exponent, 23 bits of mantissa
        return java.lang.Float.intBitsToFloat(sign << 31 | exponent << 23 | mantissa);
    }

    @Override
    public int intValue() {
        return (int) floatValue();
    }

    @Override
    public long longValue() {
        return (long) floatValue();
    }

    @Override
    public double doubleValue() {
        return floatValue();
    }

    @Override
    public long length() {
        return 2;
    }
}
