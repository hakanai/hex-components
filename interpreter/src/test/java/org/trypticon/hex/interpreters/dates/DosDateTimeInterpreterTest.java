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

package org.trypticon.hex.interpreters.dates;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.binary.BinaryFactory;

/**
 * Tests for {@link DosDateTimeInterpreter}.
 *
 * @author trejkaz
 */
public class DosDateTimeInterpreterTest {
    @Test
    public void testInterpret() {
        Interpreter<DateTime> interpreter = new DosDateTimeInterpreter();

        byte[] data = { 0x67, 0x64, (byte) 0xAC, (byte) 0x2E };

        assertDateTimeEquals(2003, 5, 12, 12, 35, 14,
                             interpreter.interpret(BinaryFactory.wrap(data), 0, 4));

    }

    private static void assertDateTimeEquals(int year, int month, int day,
                                             int hour, int minute, int second,
                                             DateTime value) {

        assertThat(value.getDate(), allOf(
                hasProperty("year", equalTo(year)),
                hasProperty("month", equalTo(month)),
                hasProperty("day", equalTo(day))));
        assertThat(value.getTime(), allOf(
                hasProperty("hour", equalTo(hour)),
                hasProperty("minute", equalTo(minute)),
                hasProperty("second", equalTo(second))));
    }
}
