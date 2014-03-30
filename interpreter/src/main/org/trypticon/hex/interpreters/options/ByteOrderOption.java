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

package org.trypticon.hex.interpreters.options;

import org.trypticon.hex.util.Format;
import org.trypticon.hex.util.Localisable;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Options for the byte order.
 */
public enum ByteOrderOption implements Localisable {
    /**
     * Big endian (highest order bytes first.)
     */
    BIG_ENDIAN,

    /**
     * Little endian (highest order bytes last.)
     */
    LITTLE_ENDIAN;

    @Override
    public String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.DISPLAY));
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return ResourceBundle.getBundle("org/trypticon/hex/interpreters/Bundle")
                .getString(String.format("ByteOrderOption.%s.%s", name(), style.name()));
    }

    @Override
    public String toString() {
        return toLocalisedString(Format.LONG);
    }
}
