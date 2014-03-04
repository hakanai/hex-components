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

package org.trypticon.hex.interpreters.primitives.floating;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.LocalisedName;

import java.util.Map;

/**
 * Info for {@link Float32InterpreterBE}.
 *
 * @author trejkaz
 */
public class Float32InterpreterBEInfo extends AbstractInterpreterInfo {
    public Float32InterpreterBEInfo() {
        super(new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Float32BE"));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        return new Float32InterpreterBE();
    }
}
