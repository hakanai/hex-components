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

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.interpreters.primitives.LittleEndian;

import javax.annotation.Nonnull;

/**
 * Interpreter for Windows NT {@code FILETIME} values, which are used in a number of other formats.
 *
 * @author trejkaz
 */
public class WindowsFileTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    // Computed using Calendar for January 1, 1601 00:00 UTC and then just taking the value.
    private static final long EPOCH = -11644473600000L;
    private static final int NANOS_PER_TIME_UNIT = 100;
    private static final int TIME_UNITS_PER_MILLISECOND = 10000;

    public WindowsFileTimeInterpreter() {
        super(DateTime.class, 8);
    }

    @Nonnull
    @Override
    protected DateTime interpret(@Nonnull Binary binary, long position) {
        // Value is the number of 100-nanosecond intervals since January 1, 1601 (UTC)
        long value = LittleEndian.getLong(binary, position);

        long millis = value / TIME_UNITS_PER_MILLISECOND;
        int remainingNanos = (int) (value % TIME_UNITS_PER_MILLISECOND) * NANOS_PER_TIME_UNIT;

        return new EpochDateTime(8, EPOCH, millis, remainingNanos);
    }
}
