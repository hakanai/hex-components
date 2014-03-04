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

import org.trypticon.hex.util.Name;
import org.trypticon.hex.util.NameStyle;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Base convenience class for implementing interpreter information.
 *
 * @author trejkaz
 */
public abstract class AbstractInterpreterInfo implements InterpreterInfo {
    private final Name name;

    protected AbstractInterpreterInfo(Name name) {
        this.name = name;
    }

    @Override
    public final String getLocalisedName(NameStyle nameStyle) {
        return name.getLocalisedName(nameStyle);
    }

    @Override
    public final String getLocalisedName(NameStyle style, Locale locale) {
        return name.getLocalisedName(style, locale);
    }

    @Override
    public List<Option> getOptions() {
        return Collections.emptyList();
    }

}
