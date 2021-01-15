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

import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.primitives.IntBitField;
import org.trypticon.hex.interpreters.primitives.LittleEndian;

import javax.annotation.Nonnull;

/**
 * Interpreter for MS-DOS time values, which are used in a number of other formats.
 *
 * @author trejkaz
 */
public class DosTimeInterpreter extends AbstractFixedLengthInterpreter<Time> {
    private static final IntBitField second = IntBitField.lowest(5);
    private static final IntBitField minute = second.next(6);
    private static final IntBitField hour = minute.next(5);
    private static final int SECOND_RESOLUTION = 2;

    public DosTimeInterpreter() {
        super(Time.class, 2);
    }

    @Nonnull
    @Override
    public Time interpret(@Nonnull Binary binary, long position) {
        return new DosTime(LittleEndian.getShort(binary, position));
    }

    private static class DosTime extends AbstractTime {
        private final short value;
        private DosTime(short value) {
            this.value = value;
        }

        @Override
        public int getHour() {
            return hour.evaluate(value);
        }

        @Override
        public int getMinute() {
            return minute.evaluate(value);
        }

        @Override
        public int getSecond() {
            return second.evaluate(value) * SECOND_RESOLUTION;
        }

        @Override
        public long length() {
            return 2;
        }
    }
}
