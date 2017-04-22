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
 * Interpreter for Windows NT {@code SYSTEMTIME} values, which are used in a number of other formats.
 *
 * @author trejkaz
 */
public class WindowsSystemTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {

    public WindowsSystemTimeInterpreter() {
        super(DateTime.class, 16);
    }

    @Nonnull
    @Override
    protected DateTime interpret(@Nonnull Binary binary, long position) {

        // typedef struct _SYSTEMTIME {
        //     WORD wYear;
        //     WORD wMonth;
        //     WORD wDayOfWeek;
        //     WORD wDay;
        //     WORD wHour;
        //     WORD wMinute;
        //     WORD wSecond;
        //     WORD wMilliseconds;
        // } SYSTEMTIME;

        return new SimpleDateTime(
                new SystemDate(LittleEndian.getUShort(binary, position),
                               LittleEndian.getUShort(binary, position + 2), // skipping day of week at position + 4
                               LittleEndian.getUShort(binary, position + 6)),
                new SystemTime(LittleEndian.getUShort(binary, position + 8),
                               LittleEndian.getUShort(binary, position + 10),
                               LittleEndian.getUShort(binary, position + 12),
                               LittleEndian.getUShort(binary, position + 14)));
    }

    private static class SystemDate extends AbstractDate {
        private final int year;
        private final int month;
        private final int day;

        private SystemDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        @Override
        public int getYear() {
            return year;
        }

        @Override
        public int getMonth() {
            return month;
        }

        @Override
        public int getDay() {
            return day;
        }

        @Override
        public long length() {
            return 8;
        }
    }

    private static class SystemTime extends AbstractTime {
        private final int hour;
        private final int minute;
        private final int second;
        private final int millisecond;

        private SystemTime(int hour, int minute, int second, int millisecond) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.millisecond = millisecond;
        }

        @Override
        public int getHour() {
            return hour;
        }

        @Override
        public int getMinute() {
            return minute;
        }

        @Override
        public int getSecond() {
            return second;
        }

        @Override
        public int getNanos() {
            return millisecond * DateConversion.NANOS_IN_MILLISECOND;
        }

        @Override
        public long length() {
            return 8;
        }
    }
}
