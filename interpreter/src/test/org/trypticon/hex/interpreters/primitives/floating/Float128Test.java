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

import org.junit.Test;
import org.trypticon.hex.util.Format;

import java.math.BigInteger;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Float128}.
 *
 * @author trejkaz
 */
public class Float128Test {

    @Test
    public void test1() {
        check("3fff 0000 0000 0000 0000 0000 0000 0000", 1.0, "1.0");
    }

    @Test
    public void testMinus1() {
        check("bfff 0000 0000 0000 0000 0000 0000 0000", -1.0, "-1.0");
    }

    @Test
    public void testMinus2() {
        check("c000 0000 0000 0000 0000 0000 0000 0000", -2.0, "-2.0");
    }

    @Test
    public void testPositiveInfinity() {
        check("7fff 0000 0000 0000 0000 0000 0000 0000", Double.POSITIVE_INFINITY, "\u221e");
    }

    @Test
    public void testNegativeInfinity() {
        check("ffff 0000 0000 0000 0000 0000 0000 0000", Double.NEGATIVE_INFINITY, "-\u221e");
    }

    @Test
    public void testMaxValue() {
        check("7ffe ffff ffff ffff ffff ffff ffff ffff", Double.POSITIVE_INFINITY,
              "1.189731495357231765085759326628007E+4932");
    }

    @Test
    public void testMinValue() {
        check("fffe ffff ffff ffff ffff ffff ffff ffff", Double.NEGATIVE_INFINITY,
              "-1.189731495357231765085759326628007E+4932");
    }

    @Test
    public void testMaxDoubleValue() {
        // Max double is 7fef ffff ffff ffff
        // exponent = 0x7fe = 2046. Bias is 1023 so that is E+1023.
        // mantissa = 0xfffffffffffff
        // under 128-bit, exponent becomes 1023 + 16383 = 17406 = 0x43FE
        check("43fe ffff ffff ffff f000 0000 0000 0000", Double.MAX_VALUE,
              "1.797693134862315708145274237317044E+308");
    }

    @Test
    public void testJustOverMaxDoubleValue() {
        check("43ff 1000 0000 0000 0000 0000 0000 0000", Double.POSITIVE_INFINITY,
              "1.910048955791210651962386765213339E+308");
    }

    @Test
    public void testMinDoubleNormal() {
        // Min normalised double is 0x1.0p-1022 (2.2250738585072014E-308, 0x 0010 0000 0000 0000)
        // exponent = -1022, so under 128-bit it becomes -1022 + 16383 = 15361 = 0x3C01
        check("3c01 0000 0000 0000 0000 0000 0000 0000", Double.MIN_NORMAL,
              "2.225073858507201383090232717332404E-308");
    }

    @Test
    public void testMinDoubleSubnormal() {
        // Min double is 0x0.0000000000001P-1022 (4.9e-324, 0x 0000 0000 0000 0001)
        // Rewriting as 1.0P-1074
        // exponent = -1074, so under 128-bit it becomes -1074 + 16383 = 15309 = 0x3BCD
        check("3bcd 0000 0000 0000 0000 0000 0000 0000", Double.MIN_VALUE,
              "4.940656458412465441765687928682214E-324");
    }

    @Test
    public void testJustUnderMinDoubleSubnormal() {
        check("3bcc 0000 0000 0000 0000 0000 0000 0000", 0.0,
              "2.470328229206232720882843964341107E-324");
    }

    @Test
    public void testPositiveZero() {
        check("0000 0000 0000 0000 0000 0000 0000 0000", 0.0, "0.0");
    }

    @Test
    public void testNegativeZero() {
        check("8000 0000 0000 0000 0000 0000 0000 0000", -0.0, "-0.0");
    }

    @Test
    public void testOneThird() {
        check("3ffd 5555 5555 5555 5555 5555 5555 5555", 0.3333333333333333,
              "0.3333333333333333333333333333333333");
    }

    @Test
    public void testLocalisation() {
        Float128 float128 = stringToFloat128("43ff 1000 0000 0000 0000 0000 0000 0000");

        assertThat(float128.toLocalisedString(Format.LONG, Locale.FRENCH),
                   is(equalTo("1,910048955791210651962386765213339E+308")));
    }

    private void check(String value, double expectedDownCast, String expectedString) {
        Float128 float128 = stringToFloat128(value);
        assertThat(float128.doubleValue(), is(expectedDownCast));
        assertThat(float128.toString(), is(equalTo(expectedString)));
    }

    private Float128 stringToFloat128(String value) {
        BigInteger valueBig = new BigInteger(value.replace(" ", ""), 16);
        long low = valueBig.longValue();
        long high = valueBig.shiftRight(64).longValue();
        return new Float128(high, low);
    }
}
