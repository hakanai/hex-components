/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2013  Trejkaz, Hex Project
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

/**
 * Interpreter for Windows OLE {@code VT_DATE} / {@code PT_APPTIME} values, common in Office formats, MAPI, etc.
 *
 * @author trejkaz
 */
public class WindowsOleDateInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    // Computed using Calendar for December 30, 1899 00:00 UTC and then just taking the value.
    private static final long EPOCH = -2209161600000L;

    public WindowsOleDateInterpreter() {
        super(DateTime.class, 8);
    }

    @Override
    protected DateTime interpret(Binary binary, long position) {
        // value is the number of days since December 30, 1899.
        double value = Double.longBitsToDouble(LittleEndian.getLong(binary, position));

        // When the value is negative, the whole part of the number becomes the day offset, i.e. -1.5
        // would be 0.5 of a day after December 29, 1899. So when the value is negative, you have
        // to round up instead of down and then use the absolute value of the remainder of the day.
        //
        // http://blogs.msdn.com/b/ericlippert/archive/2003/09/16/eric-s-complete-guide-to-vt-date.aspx
        //
        // This article also says that the OS rounds the remainder to 0.5 seconds, but since we're pulling
        // out the actual value, there is a conscious decision to keep the precision which was present.

        int wholeDays = value > 0 ? (int) Math.floor(value)
                                  : (int) Math.ceil(value);
        long nanosInDay = (long) (Math.abs(value - wholeDays) * DateConversion.NANOS_IN_DAY);

        long millis = wholeDays * DateConversion.MILLIS_IN_DAY + nanosInDay / DateConversion.NANOS_IN_MILLISECOND;
        int remainingNanos = (int) (nanosInDay % DateConversion.NANOS_IN_MILLISECOND);

        return new EpochDateTime(8, EPOCH, millis, remainingNanos);
    }
}
