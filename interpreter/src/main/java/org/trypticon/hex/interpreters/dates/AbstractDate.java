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

import org.trypticon.hex.util.Format;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Helper abstract class for defining new types of date.
 *
 * @author trejkaz
 */
public abstract class AbstractDate implements Date {
    @Override
    public boolean equals(Object o) {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Date))
        {
            return false;
        }

        Date that = (Date) o;

        return getDay() == that.getDay() && getMonth() == that.getMonth() && getYear() == that.getYear();
    }

    @Override
    public int hashCode() {
        int result = getYear();
        result = 31 * result + getMonth();
        result = 31 * result + getDay();
        return result;
    }

    @Override
    public String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.MEDIUM, null)
                .toFormatter(locale)
                .withZone(ZoneOffset.UTC)
                .format(LocalDate.of(getYear(), getMonth(), getDay()));
    }

    @Override
    public String toString() {
        return toLocalisedString(Format.LONG, Locale.ROOT);
    }
}
