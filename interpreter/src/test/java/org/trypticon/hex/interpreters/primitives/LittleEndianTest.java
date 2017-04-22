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
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link LittleEndian}.
 *
 * @author trejkaz
 */
public class LittleEndianTest {

    @Test
    public void testGetShort() {
        Binary binary = BinaryFactory.wrap(new byte[] { 0x01, 0x02, (byte) 0xC1, (byte) 0xC2 });
        assertEquals("Wrong value", (short) 0x0201, LittleEndian.getShort(binary, 0));
        assertEquals("Wrong value", (short) 0xC2C1, LittleEndian.getShort(binary, 2));
    }

    @Test
    public void testGetUShort() {
        Binary binary = BinaryFactory.wrap(new byte[] { 0x01, 0x02, (byte) 0xC1, (byte) 0xC2 });
        assertEquals("Wrong value", 0x0201, LittleEndian.getUShort(binary, 0));
        assertEquals("Wrong value", 0xC2C1, LittleEndian.getUShort(binary, 2));
    }
}
