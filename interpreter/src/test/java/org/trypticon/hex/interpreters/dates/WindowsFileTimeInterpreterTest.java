/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.dates;

import org.junit.Test;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link WindowsFileTimeInterpreter}.
 *
 * @author trejkaz
 */
public class WindowsFileTimeInterpreterTest {
    @Test
    public void testSomeValue() {
        Binary binary = BinaryFactory.wrap(new byte[] {
                (byte) 0x6D,
                (byte) 0x1E,
                (byte) 0xCF,
                (byte) 0xEB,
                (byte) 0x98,
                (byte) 0xEE,
                (byte) 0xCD,
                (byte) 0x01,
        });
        DateTime value = new WindowsFileTimeInterpreter().interpret(binary, 0);
        assertThat(value.toString(), is(equalTo("Jan 9, 2013 6:41:22 PM")));
    }
}
