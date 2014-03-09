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

import org.trypticon.hex.util.Format;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Helper abstract class for defining new types of time.
 *
 * @author trejkaz
 */
public abstract class AbstractTime implements Time {

    @Override
    public int getNanos() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Time))
        {
            return false;
        }

        Time that = (Time) o;

        return getHour() == that.getHour() && getMinute() == that.getMinute() &&
               getSecond() == that.getSecond() && getNanos() == that.getNanos();
    }

    @Override
    public int hashCode() {
        int result = getHour();
        result = 31 * result + getMinute();
        result = 31 * result + getSecond();
        result = 31 * result + getNanos();
        return result;
    }

    @Override
    public String getLocalisedName(Format style) {
        return getLocalisedName(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    @Override
    public String getLocalisedName(Format style, Locale locale) {
        //TODO: How do nanoseconds fit into this?
        DateFormat format = DateFormat.getTimeInstance(DateFormat.LONG, locale);
        Calendar calendar = new GregorianCalendar(locale);
        calendar.set(2001, Calendar.JANUARY, 1, getHour(), getMinute(), getSecond());
        return format.format(calendar.getTime());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(18);
        builder.append(String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond()));
        if (getNanos() != 0) {
            // XXX: Really it should print only as many decimal places as are present.
            builder.append(String.format("%09d", getNanos()));
        }
        return builder.toString();
    }
}
