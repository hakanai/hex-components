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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Interpreter for Windows NT {@code FILETIME} values, which are used in a number of other formats.
 *
 * @author trejkaz
 */
public class WindowsFileTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {

    public WindowsFileTimeInterpreter() {
        super(DateTime.class, 8);
    }

    @Override
    protected DateTime interpret(Binary binary, long position) {
        return new FileTime(LittleEndian.getLong(binary, position));
    }

    private static class FileTime extends AbstractCalendarDateTime {
        // Computed using Calendar for the appropriate date and time and then just taking the value.
        private static final long EPOCH = -11644473600000L;

        private final long millis;
        private final int remainingNanos;

        private FileTime(long value) {
            // Value is the number of 100-nanosecond intervals since January 1, 1601 (UTC)
            millis = value / 10000;
            remainingNanos = (int) (value - millis * 10000) * 100;
        }

        @Override
        protected Calendar createCalendar() {
            Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            // add() doesn't support long so we manually add it like this.
            calendar.setTimeInMillis(EPOCH + millis);
            return calendar;
        }

        @Override
        public Time getTime() {
            return new CalendarTime() {
                @Override
                public int getNanos() {
                    // Adding back the nanoseconds we had to take off to conform to the Calendar API.
                    return super.getNanos() + remainingNanos;
                }
            };
        }

        @Override
        public long length() {
            return 8;
        }
    }
}
