/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.primitives.unsigned;

import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.primitives.BigEndian;

/**
 * Interpreter for unsigned short values.
 *
 * @author trejkaz
 */
public class UShortInterpreterBE extends AbstractFixedLengthInterpreter<UShort> {
    public UShortInterpreterBE() {
        super(UShort.class, 2);
    }

    @Override
    public Class<UShort> getType() {
        return UShort.class;
    }

    @Override
    public UShort interpret(Binary binary, long position) {
        return new UShort(BigEndian.getShort(binary, position));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof UShortInterpreterBE;
    }

    @Override
    public int hashCode() {
        return 100161;
    }

    @Override
    public String toString() {
        return "uint2be";
    }
}
