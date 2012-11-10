/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.binary.Binary;

/**
 * Interpreter for unsigned int values.
 *
 * @author trejkaz
 */
public class UIntInterpreterLE extends AbstractFixedLengthInterpreter<UInt> {
    public UIntInterpreterLE() {
        super(UInt.class, 4);
    }

    public UInt interpret(Binary binary, long position) {
        return new UInt(LittleEndian.getInt(binary, position));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof UIntInterpreterLE;
    }

    @Override
    public int hashCode() {
        return 100322;
    }

    @Override
    public String toString() {
        return "uint4le";
    }
}
