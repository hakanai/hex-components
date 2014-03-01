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

import java.util.Calendar;

/**
 * Abstract date-time value using {@link Calendar} to compute the date.
 */
public abstract class AbstractCalendarDateTime implements DateTime {
    private Calendar calendar;

    @Override
    public Date getDate() {
        return new CalendarDate();
    }

    @Override
    public Time getTime() {
        return new CalendarTime();
    }

    private Calendar getCalendar() {
        if (calendar == null) {
            calendar = createCalendar();
        }
        return calendar;
    }

    /**
     * Implemented by subclasses to create a calendar pointing at the appropriate date/time.
     *
     * @return the calendar.
     */
    protected abstract Calendar createCalendar();

    protected class CalendarDate extends AbstractDate {
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

    protected class CalendarTime extends AbstractTime {
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
            return getCalendar().get(Calendar.MILLISECOND) * 1000000;
        }

        @Override
        public long length() {
            throw new UnsupportedOperationException("Length is meaningless for this type of time");
        }
    }

}
