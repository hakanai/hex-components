/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
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

import javax.annotation.Nonnull;
import java.lang.*;

/**
 * Interprets 4-byte floating point numbers in IEEE754 format.
 *
 * @author trejkaz
 */
public class Float32InterpreterLE extends AbstractFixedLengthInterpreter<Float32> {
    public Float32InterpreterLE() {
        super("float4le", Float32.class, 4);
    }

    @Nonnull
    @Override
    public Float32 interpret(@Nonnull Binary binary, long position) {
        return new Float32(java.lang.Float.intBitsToFloat(LittleEndian.getInt(binary, position)));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof Float32InterpreterLE;
    }

    @Override
    public int hashCode() {
        return 101321;
    }
}
