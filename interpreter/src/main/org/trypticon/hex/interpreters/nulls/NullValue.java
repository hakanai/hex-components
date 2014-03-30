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

package org.trypticon.hex.interpreters.nulls;

import org.trypticon.hex.interpreters.AbstractValue;
import org.trypticon.hex.util.Format;

import java.util.Locale;

/**
 * A value with no meaning.
 *
 * @author trejkaz
 */
public class NullValue extends AbstractValue {
    private final long length;

    NullValue(long length) {
        this.length = length;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return "";
    }
}
