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

package org.trypticon.hex.interpreters.primitives.floating;

import org.trypticon.hex.interpreters.primitives.AbstractNumberValue;
import org.trypticon.hex.util.Format;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Wraps a Java {@code float} as a {@code Value}.
 *
 * @author trejkaz
 */
public class Float32 extends AbstractNumberValue {
    private final float value;

    public Float32(float value) {
        this.value = value;
    }

    public byte getValue() {
        return (byte) value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
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
    public String getLocalisedName(Format style, Locale locale) {
        if (Float.isInfinite(value) || Float.isNaN(value) || value == 0.0f) {
            return NumberFormat.getInstance(locale).format(value);
        } else {
            return String.format(locale, "%.8G", value);
        }
    }
}
