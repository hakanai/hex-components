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

package org.trypticon.hex.interpreters.meta;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.FixedLengthInterpreterInfo;
import org.trypticon.hex.util.Localisable;

import java.util.Map;

/**
 * Implements the common case of a single interpreter with no options.
 */
public class SingleFixedLengthInterpreterInfo extends AbstractInterpreterInfo implements FixedLengthInterpreterInfo {
    // Since interpreters are assumed to carry no state, you can share the instances.
    private final FixedLengthInterpreter interpreter;

    public SingleFixedLengthInterpreterInfo(Localisable name, FixedLengthInterpreter interpreter) {
        super(name);
        this.interpreter = interpreter;
    }

    @Override
    public long getValueLength() {
        return interpreter.getValueLength();
    }

    @Override
    public FixedLengthInterpreter<?> create(Map<String, Object> options) {
        return interpreter;
    }
}
