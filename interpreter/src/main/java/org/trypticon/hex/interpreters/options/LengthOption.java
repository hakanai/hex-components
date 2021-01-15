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

package org.trypticon.hex.interpreters.options;

import org.trypticon.hex.util.Format;
import org.trypticon.hex.util.Localisable;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Options for the length of an auto-length structure.
 *
 * @author trejkaz
 */
public enum LengthOption implements Localisable {
    /**
     * Determine the length automatically (via the selected length or whatever.)
     */
    AUTO,

    // Fixed bit lengths.
    BIT_8,
    BIT_16,
    BIT_32,
    BIT_64,
    BIT_128;

    /**
     * Tries to create a {@code LengthOption} from a byte count.
     *
     * @param bytes the byte length.
     * @return the length option.
     * @throws java.lang.IllegalArgumentException if the value is unknown.
     */
    public static LengthOption fromBytes(long bytes) {
        if (bytes <= 0 || bytes > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Unknown number of bytes: " + bytes);
        }

        switch ((int) bytes) {
            case 1:
                return LengthOption.BIT_8;
            case 2:
                return LengthOption.BIT_16;
            case 4:
                return LengthOption.BIT_32;
            case 8:
                return LengthOption.BIT_64;
            case 16:
                return LengthOption.BIT_128;
            default:
                throw new IllegalArgumentException("Unknown number of bytes: " + bytes);
        }
    }

    @Override
    public String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.DISPLAY));
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return ResourceBundle.getBundle("org/trypticon/hex/interpreters/Bundle")
                .getString(String.format("LengthOption.%s.%s", name(), style.name()));
    }

    @Override
    public String toString() {
        return toLocalisedString(Format.LONG);
    }
}
