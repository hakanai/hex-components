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

package org.trypticon.hex;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link HexUtils}.
 *
 * @author trejkaz
 */
public class HexUtilsTest {

    @Test
    public void testToHex() {
        assertThat(HexUtils.toHex((byte) 0x0A), is("0A"));
        assertThat(HexUtils.toHex((byte) 0xCA), is("CA"));
    }

    @Test
    public void testToAscii() {
        assertThat(HexUtils.toAscii((byte) 0x00), is("·"));
        assertThat(HexUtils.toAscii((byte) 0x1F), is("·"));

        assertThat(HexUtils.toAscii((byte) 0x20), is(" "));
        assertThat(HexUtils.toAscii((byte) 0x55), is("U"));

        assertThat(HexUtils.toAscii((byte) 0xCA), is("·"));
        assertThat(HexUtils.toAscii((byte) 0xFE), is("·"));
        assertThat(HexUtils.toAscii((byte) 0xBA), is("·"));
        assertThat(HexUtils.toAscii((byte) 0xBE), is("·"));
    }
}
