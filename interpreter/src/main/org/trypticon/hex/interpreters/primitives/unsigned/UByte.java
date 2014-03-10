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

import org.trypticon.hex.interpreters.primitives.AbstractNumberValue;
import org.trypticon.hex.util.Format;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * An unsigned byte value.
 *
 * @author trejkaz
 */
public class UByte extends AbstractNumberValue {
    private final byte value;

    public UByte(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    @Override
    public int intValue() {
        return value & 0xFF;
    }

    @Override
    public long longValue() {
        return value & 0xFF;
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
        return 1;
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return NumberFormat.getInstance().format(shortValue());
    }
}
