/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.primitives.signed;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;

/**
 * Interpreter for signed byte values.
 *
 * @author trejkaz
 */
public class SByteInterpreter extends AbstractFixedLengthInterpreter<SByte> {
    public SByteInterpreter() {
        super(SByte.class, 1);
    }

    @Override
    public SByte interpret(Binary binary, long position) {
        return new SByte(binary.read(position));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof SByteInterpreter;
    }

    @Override
    public int hashCode() {
        return 100081;
    }

    @Override
    public String toString() {
        return "sint1";
    }
}
