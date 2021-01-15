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

package org.trypticon.hex.interpreters.strings;

import org.junit.Test;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link BinaryStringInterpreter}.
 *
 * @author trejkaz
 */
public class BinaryStringInterpreterTest {
    @Test
    public void testInterpreting() throws Exception {
        Binary binary = BinaryFactory.wrap("This is a test".getBytes("UTF-8"));
        assertThat(new BinaryStringInterpreter().interpret(binary, 0, 4).toString(),
                   is("This"));
    }

    @Test
    public void testInterpretingOverNullTerminator() throws Exception {
        Binary binary = BinaryFactory.wrap("Th\0is is a test".getBytes("UTF-8"));
        assertThat(new BinaryStringInterpreter().interpret(binary, 0, 4).toString(),
                   is("Th\0i"));
    }
}
