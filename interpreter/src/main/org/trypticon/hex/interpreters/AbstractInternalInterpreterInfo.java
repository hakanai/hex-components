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

package org.trypticon.hex.interpreters;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Base class for interpreter information implemented in this module where we know the location of
 * the resource bundle and the convention for all the resource keys.
 */
public abstract class AbstractInternalInterpreterInfo extends AbstractInterpreterInfo {
    private final String baseKey;

    /**
     * Constructs the interpreter info.
     *
     * @param baseKey the base key for looking up the names of the interpreter in the resources.
     */
    protected AbstractInternalInterpreterInfo(String baseKey) {
        this.baseKey = baseKey;
    }

    @Override
    public final String getLocalisedName(NameStyle style, Locale locale) {
        return ResourceBundle.getBundle("org/trypticon/hex/interpreters/Bundle")
                .getString(String.format("Interpreters.%s.%s", baseKey, style));
    }
}
