/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
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

import org.trypticon.hex.interpreters.primitives.AbstractNumberValue;
import org.trypticon.hex.interpreters.primitives.LongBitField;
import org.trypticon.hex.util.Format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * IEEE 754 quadruple-precision (128-bit) floating point.
 *
 * @author trejkaz
 */
public class Float128 extends AbstractNumberValue {
    private final long encodedValueHigh;
    private final long encodedValueLow;

    public Float128(long encodedValueHigh, long encodedValueLow) {
        this.encodedValueHigh = encodedValueHigh;
        this.encodedValueLow = encodedValueLow;
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return new Float128Fields(encodedValueHigh, encodedValueLow).toDouble();
    }

    @Override
    public long length() {
        return 16;
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return new Float128Fields(encodedValueHigh, encodedValueLow).toLocalisedString(locale);
    }

    /**
     * A breakdown of the fields to make the value a bit easier to work with.
     */
    private static class Float128Fields {
        // Breakdown of the format. the lowest 64 bits of the mantissa are stored in encodedValueLow,
        // so we only have to break down encodedValueHigh.
        private static final LongBitField mantissaField = LongBitField.lowest(48);
        private static final LongBitField exponentField = mantissaField.next(15);
        private static final LongBitField signField = exponentField.next(1);

        private static final int EXPONENT_BIAS = 16383;
        private static final int MANTISSA_SIZE = 48 + 64;
        private static final BigInteger mantissaImplicitLeading1 = BigInteger.ONE.shiftLeft(MANTISSA_SIZE);

        // Do actual maths at a slightly higher precision in an attempt to mitigate precision loss.
        private static final MathContext mathContext = new MathContext(36, RoundingMode.HALF_EVEN);
        // When converting to string, match the precision given by people when they show these numbers in articles.
        private static final MathContext toStringMathContext = new MathContext(34, RoundingMode.HALF_EVEN);

        private final BigInteger mantissa;
        private final int exponent;
        private final int sign;

        private Float128Fields(long encodedValueHigh, long encodedValueLow) {
            long mantissaHigh = mantissaField.evaluate(encodedValueHigh);
            BigInteger mantissaLow = BigInteger.valueOf(encodedValueLow)
                    .and(BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE));
            mantissa = BigInteger.valueOf(mantissaHigh).shiftLeft(64).or(mantissaLow);
            exponent = (int) exponentField.evaluate(encodedValueHigh);
            sign = (int) signField.evaluate(encodedValueHigh);
        }

        private boolean isInfinity() {
            return exponent == 0x7fff && mantissa.signum() == 0;
        }

        private boolean isNaN() {
            return exponent == 0x7fff && mantissa.signum() != 0;
        }

        private boolean isZero() {
            return exponent == 0 && mantissa.signum() == 0;
        }

        private boolean isSubnormal() {
            return exponent == 0 && mantissa.signum() != 0;
        }

        private BigDecimal toBigDecimal() {
            BigInteger actualMantissa;
            int actualExponent;
            if (isSubnormal()) {
                actualExponent = exponent - EXPONENT_BIAS + 1 - MANTISSA_SIZE;
                actualMantissa = mantissa;
            } else {
                actualExponent = exponent - EXPONENT_BIAS - MANTISSA_SIZE;
                actualMantissa = mantissa.add(mantissaImplicitLeading1);
            }
            BigDecimal result = new BigDecimal(actualMantissa, 0, mathContext)
                    .multiply(new BigDecimal(2.0, mathContext).pow(actualExponent, mathContext), mathContext);
            if (sign == 1) {
                result = result.negate();
            }
            return result;
        }

        private double toDouble() {
            if (isInfinity()) {
                return sign == 1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (isNaN()) {
                return Double.NaN;
            } else if (isZero()) {
                return sign == 1 ? -0.0 : 0.0;
            } else {
                return toBigDecimal().doubleValue();
            }
        }

        private String toLocalisedString(Locale locale) {
            NumberFormat format = NumberFormat.getInstance(locale);
            format.setMinimumFractionDigits(1);
            if (isInfinity() || isNaN() || isZero()) {
                return format.format(toDouble());
            } else {
                BigDecimal bigDecimal = toBigDecimal().round(toStringMathContext);

                // The only way I could figure out to find out the number of digits.
                bigDecimal = bigDecimal.stripTrailingZeros();

                int precision = Math.max(Math.min(bigDecimal.precision() + 1, 34), 2);

                return String.format(locale, "%." + precision + "G", bigDecimal);
            }
        }
    }
}
