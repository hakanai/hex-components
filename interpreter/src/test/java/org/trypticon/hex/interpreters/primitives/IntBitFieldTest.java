/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link IntBitField}.
 *
 * @author trejkaz
 */
public class IntBitFieldTest {

    @Test
    public void testBitFieldConstruction() {

        // Three reads of 5 bits at a time, just to make sure it shifts correctly.
        IntBitField last5Bits = IntBitField.lowest(5);
        assertEquals("Wrong value", 0x1F, last5Bits.evaluate(0xFFFF));
        assertEquals("Wrong value", 0x1F, last5Bits.next(5).evaluate(0xFFFF));
        assertEquals("Wrong value", 0x1F, last5Bits.next(5).next(5).evaluate(0xFFFF));
    }
}
