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

import org.trypticon.hex.util.Format;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Base class for implementing date and time.
 *
 * @author trejkaz
 */
public abstract class AbstractDateTime implements DateTime {
    @Override
    public String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
        Calendar calendar = new GregorianCalendar(locale);
        Date date = getDate();
        Time time = getTime();
        //noinspection MagicConstant
        calendar.set(date.getYear(), date.getMonth() - 1, date.getDay(),
                     time.getHour(), time.getMinute(), time.getSecond());
        return format.format(calendar.getTime());
    }

    @Override
    public String toString() {
        return toLocalisedString(Format.LONG, Locale.ROOT);
    }
}
