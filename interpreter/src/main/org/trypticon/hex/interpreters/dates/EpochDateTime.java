/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * A date-time value using {@link Calendar} to compute the date based on an epoch
 * and the offsets the subclass passes in.
 */
public class EpochDateTime extends AbstractDateTime {
    private final int length;
    private final long millis;
    private final int remainingNanos;
    private Calendar calendar;

    /**
     * Constructs the date-time.
     *
     * @param length the length of the structure.
     * @param epoch the epoch to calculate the time from
     * @param millisSinceEpoch the number of milliseconds since the epoch.
     * @param remainingNanos any remaining nanoseconds which should be taken into account (workaround for
     *                       the APIs not letting us put them into the date value until Java 8.)
     */
    public EpochDateTime(int length, long epoch, long millisSinceEpoch, int remainingNanos) {
        this.length = length;
        this.millis = epoch + millisSinceEpoch;
        this.remainingNanos = remainingNanos;
    }

    @Override
    public Date getDate() {
        return new CalendarDate();
    }

    @Override
    public Time getTime() {
        return new CalendarTime();
    }

    @Override
    public long length() {
        return length;
    }

    private Calendar getCalendar() {
        if (calendar == null) {
            calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            // add() doesn't support long so we manually add it like this.
            calendar.setTimeInMillis(millis);
        }
        return calendar;
    }

    private class CalendarDate extends AbstractDate {
        @Override
        public int getYear() {
            return getCalendar().get(Calendar.YEAR);
        }

        @Override
        public int getMonth() {
            // Calendar API months are zero-indexed.
            return getCalendar().get(Calendar.MONTH) + 1;
        }

        @Override
        public int getDay() {
            return getCalendar().get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public long length() {
            throw new UnsupportedOperationException("Length is meaningless for this type of date");
        }
    }

    private class CalendarTime extends AbstractTime {
        @Override
        public int getHour() {
            return getCalendar().get(Calendar.HOUR_OF_DAY);
        }

        @Override
        public int getMinute() {
            return getCalendar().get(Calendar.MINUTE);
        }

        @Override
        public int getSecond() {
            return getCalendar().get(Calendar.SECOND);
        }

        @Override
        public int getNanos() {
            return getCalendar().get(Calendar.MILLISECOND) * DateConversion.NANOS_IN_MILLISECOND + remainingNanos;
        }

        @Override
        public long length() {
            throw new UnsupportedOperationException("Length is meaningless for this type of time");
        }
    }

}
