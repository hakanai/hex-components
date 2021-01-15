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

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link org.trypticon.hex.interpreters.primitives.IntBitField}.
 *
 * @author trejkaz
 */
public class LongBitFieldTest {

    @Test
    public void testShifting() {

        // Three reads of 5 bits at a time, just to make sure it shifts correctly.
        LongBitField last5Bits = LongBitField.lowest(5);
        assertThat(last5Bits.evaluate(0xFFFFFFFFL), is(0x1FL));
        LongBitField next5Bits = last5Bits;
        for (int i = 0; i < 5; i++) {
            next5Bits = next5Bits.next(5);
            assertThat(next5Bits.evaluate(0xFFFFFFFFL), is(0x1FL));
        }
        next5Bits = next5Bits.next(5);
        assertThat(next5Bits.evaluate(0xFFFFFFFFL), is(0x3L));
    }

    @Test
    public void testLargerThanInt() {
        LongBitField last48Bits = LongBitField.lowest(48);
        assertThat(last48Bits.evaluate(0xFFFFFFFFFFFFFFFFL), is(0xFFFFFFFFFFFFL));
    }
}
