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

package org.trypticon.hex.interpreters.primitives.unsigned;

import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.util.Format;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * An unsigned int value.
 *
 * @author trejkaz
 */
public class UInt extends Number implements Value {
    private final int value;

    public UInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value & 0xFFFFFFFFL;
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
        return 4;
    }

    @Override
    public String getLocalisedName(Format style) {
        return getLocalisedName(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    @Override
    public String getLocalisedName(Format style, Locale locale) {
        return NumberFormat.getInstance().format(longValue());
    }

    public String toString() {
        return String.valueOf(value & 0xFFFFFFFFL);
    }
}
