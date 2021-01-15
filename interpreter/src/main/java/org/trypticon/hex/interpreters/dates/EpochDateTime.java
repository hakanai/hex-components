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

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * A date-time value using {@link ZonedDateTime} to compute the date based on an epoch
 * and the offsets the subclass passes in.
 */
public class EpochDateTime extends AbstractDateTime {
    private final int length;
    private final Instant epoch;
    private final long millisSinceEpoch;
    private final int remainingNanos;
    private ZonedDateTime dateTime;

    /**
     * Constructs the date-time.
     *
     * @param length the length of the structure.
     * @param epoch the epoch to calculate the time from
     * @param millisSinceEpoch the number of milliseconds since the epoch.
     * @param remainingNanos any remaining nanoseconds which should be taken into account (workaround for
     *                       the APIs not letting us put them into the date value until Java 8.)
     */
    public EpochDateTime(int length, Instant epoch, long millisSinceEpoch, int remainingNanos) {
        this.length = length;
        this.epoch = epoch;
        this.millisSinceEpoch = millisSinceEpoch;
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

    private ZonedDateTime getDateTime() {
        if (dateTime == null) {
            dateTime = epoch.plusMillis(millisSinceEpoch).plusNanos(remainingNanos).atZone(ZoneOffset.UTC);
        }
        return dateTime;
    }

    private class CalendarDate extends AbstractDate {
        @Override
        public int getYear() {
            return getDateTime().getYear();
        }

        @Override
        public int getMonth() {
            return getDateTime().getMonthValue();
        }

        @Override
        public int getDay() {
            return getDateTime().getDayOfMonth();
        }

        @Override
        public long length() {
            throw new UnsupportedOperationException("Length is meaningless for this type of date");
        }
    }

    private class CalendarTime extends AbstractTime {
        @Override
        public int getHour() {
            return getDateTime().getHour();
        }

        @Override
        public int getMinute() {
            return getDateTime().getMinute();
        }

        @Override
        public int getSecond() {
            return getDateTime().getSecond();
        }

        @Override
        public int getNanos() {
            return getDateTime().getNano();
        }

        @Override
        public long length() {
            throw new UnsupportedOperationException("Length is meaningless for this type of time");
        }
    }

}
