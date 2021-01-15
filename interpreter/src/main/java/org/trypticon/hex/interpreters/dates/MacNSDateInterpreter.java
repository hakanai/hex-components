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

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.interpreters.primitives.BigEndian;

import javax.annotation.Nonnull;

/**
 * Interpreter for NeXTSTEP/Mac OS X {@code NSDate} / {@code CFDateRef} values.
 *
 * @author trejkaz
 */
public class MacNSDateInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    // Computed using Calendar for January 1, 2001 00:00 UTC and then just taking the value.
    private static final long EPOCH = 978307200000L;

    public MacNSDateInterpreter() {
        super("mac_osx_nsdate", DateTime.class, 8);
    }

    @Nonnull
    @Override
    protected DateTime interpret(@Nonnull Binary binary, long position) {
        // Value is the number of seconds since January 1, 2001 (UTC)
        double value = Double.longBitsToDouble(BigEndian.getLong(binary, position));

        long wholeSeconds = (long) Math.floor(value);
        long nanosInSecond = (long) (Math.abs(value - wholeSeconds) * DateConversion.NANOS_IN_SECOND);

        long millis = wholeSeconds * DateConversion.MILLIS_IN_SECOND +
                      nanosInSecond / DateConversion.NANOS_IN_MILLISECOND;
        int remainingNanos = (int) (nanosInSecond % DateConversion.NANOS_IN_MILLISECOND);

        return new EpochDateTime(8, EPOCH, millis, remainingNanos);
    }
}
