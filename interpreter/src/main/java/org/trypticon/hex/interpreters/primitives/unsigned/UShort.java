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

package org.trypticon.hex.interpreters.primitives.unsigned;

import org.trypticon.hex.interpreters.primitives.AbstractNumberValue;
import org.trypticon.hex.util.Format;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * An unsigned short value.
 *
 * @author trejkaz
 */
// We don't serialise numeric values, this is just an unfortunate side-effect of subclassing Number.
@SuppressWarnings("serial")
public class UShort extends AbstractNumberValue {
    private final short value;

    public UShort(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    @Override
    public int intValue() {
        return value & 0xFFFF;
    }

    @Override
    public long longValue() {
        return value & 0xFFFF;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public long length() {
        return 2;
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return NumberFormat.getInstance(locale).format(intValue());
    }
}
