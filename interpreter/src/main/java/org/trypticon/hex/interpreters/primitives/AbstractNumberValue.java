/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.primitives;

import org.trypticon.hex.interpreters.AbstractValue;
import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.util.Format;

import java.lang.Number;
import java.util.Locale;

/**
 * <p>Combination of {@link AbstractValue} and {@link Number}.</p>
 *
 * <p>Will probably become redundant in Java 8 once we can move the default implementations to the
 *    {@link Value} interface.</p>
 */
// We don't serialise numeric values, this is just an unfortunate side-effect of subclassing Number.
@SuppressWarnings("serial")
public abstract class AbstractNumberValue extends Number implements Value {
    @Override
    public final String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.FORMAT));
    }

    @Override
    public final String toString() {
        return toLocalisedString(Format.LONG, Locale.ROOT);
    }
}
