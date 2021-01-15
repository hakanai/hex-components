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

package org.trypticon.hex.interpreters.primitives.floating;

import org.junit.Test;
import org.trypticon.hex.util.Format;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Float16}.
 *
 * @author trejkaz
 */
public class Float16Test {

    @Test
    public void test1() {
        check(0b0_01111_0000000000, 1.0f);
    }

    @Test
    public void testMinus1() {
        check(0b1_01111_0000000000, -1.0f);
    }

    @Test
    public void testNextSmallestAfter1() {
        check(0b0_01111_0000000001, 1.0f + (float) Math.pow(2.0, -10));
    }

    @Test
    public void test2() {
        check(0b0_10000_0000000000, 2.0f);
    }

    @Test
    public void testMinus2() {
        check(0b1_10000_0000000000, -2.0f);
    }

    @Test
    public void testMaxHalfPrecision() {
        check(0b0_11110_1111111111, 65504.0f);
    }

    @Test
    public void testMaxPositiveNormal() {
        check(0b0_00001_0000000000, (float) Math.pow(2.0, -14));
    }

    @Test
    public void testMaximumSubnormal() {
        check(0b0_00000_1111111111, (float) (Math.pow(2.0, -14) - Math.pow(2.0, -24)));
    }

    @Test
    public void testMinimumPositiveSubnormal() {
        check(0b0_00000_0000000001, (float) Math.pow(2.0, -24));
    }

    @Test
    public void testPositive0() {
        check(0b0_00000_0000000000, 0.0f);
    }

    @Test
    public void testNegative0() {
        check(0b1_00000_0000000000, -0.0f);
    }

    @Test
    public void testPositiveInfinity() {
        check(0b0_11111_0000000000, java.lang.Float.POSITIVE_INFINITY);
    }

    @Test
    public void testNegativeInfinity() {
        check(0b1_11111_0000000000, java.lang.Float.NEGATIVE_INFINITY);
    }

    @Test
    public void testOneThird() {
        // one third, infinite expansion of 1.010101... x 2^(-2)
        check(0b0_01101_0101010101, java.lang.Float.intBitsToFloat(0b0_01111101_01010101010000000000000));
    }

    @Test
    public void testLocalisation() {
        Float16 float16 = new Float16((short) 0b0_11110_1111111111);

        assertThat(float16.toLocalisedString(Format.LONG, Locale.FRENCH), is(equalTo("6,550E+04")));
    }

    private void check(int value, float expectedResult) {
        assertThat(new Float16((short) value).floatValue(), is(expectedResult));
    }
}
