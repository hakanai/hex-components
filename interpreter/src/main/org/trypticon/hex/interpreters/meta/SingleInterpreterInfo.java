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

package org.trypticon.hex.interpreters.meta;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.Localisable;

import java.util.Map;

/**
 * Implements the common case of a single interpreter with no options.
 */
public class SingleInterpreterInfo extends AbstractInterpreterInfo {
    // Since interpreters are assumed to carry no state, you can share the instances.
    private final Interpreter interpreter;

    public SingleInterpreterInfo(Localisable name, Interpreter interpreter) {
        super(name);
        this.interpreter = interpreter;
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        return interpreter;
    }
}
