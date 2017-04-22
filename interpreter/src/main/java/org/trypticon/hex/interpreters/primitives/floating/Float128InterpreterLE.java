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

package org.trypticon.hex.interpreters.primitives.floating;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.interpreters.primitives.LittleEndian;

/**
 * Interprets 16-byte floating point numbers in IEEE754 format.
 *
 * @author trejkaz
 */
public class Float128InterpreterLE extends AbstractFixedLengthInterpreter<Float128> {
    public Float128InterpreterLE() {
        super(Float128.class, 16);
    }

    @Override
    public Float128 interpret(Binary binary, long position) {
        return new Float128(LittleEndian.getLong(binary, position + 8),
                            LittleEndian.getLong(binary, position));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof Float128InterpreterLE;
    }

    @Override
    public int hashCode() {
        return 102282;
    }

    @Override
    public String toString() {
        return "float16le";
    }
}
