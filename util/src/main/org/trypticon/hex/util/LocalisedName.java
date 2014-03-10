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

package org.trypticon.hex.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * An object used as a delegate to get the name of other objects from a resource bundle.
 * This exists as a separate utility to avoid having to write the code more than once.
 */
public class LocalisedName implements Localisable {
    private final String bundleName;
    private final String baseKey;

    public LocalisedName(String bundleName, String baseKey) {
        this.bundleName = bundleName;
        this.baseKey = baseKey;
    }

    @Override
    public String toLocalisedString(Format style) {
        return toLocalisedString(style, Locale.getDefault(Locale.Category.DISPLAY));
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return ResourceBundle.getBundle(bundleName, locale, Thread.currentThread().getContextClassLoader())
                .getString(baseKey + '.' + style.name());
    }
}
