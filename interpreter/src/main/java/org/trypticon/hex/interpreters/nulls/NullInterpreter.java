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

package org.trypticon.hex.interpreters.nulls;

import org.trypticon.hex.interpreters.AbstractInterpreter;
import org.trypticon.hex.binary.Binary;

/**
 * An interpreter which can mark a range as meaning nothing.  Useful for
 * when you have a non-semantic comment with no value, such as "reserved",
 * or "I don't know what this is."
 *
 * @author trejkaz
 */
public class NullInterpreter extends AbstractInterpreter<NullValue> {

    public NullInterpreter() {
        super(NullValue.class);
    }

    @Override
    public NullValue interpret(Binary binary, long position, long length) {
        return new NullValue(length);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof NullInterpreter;
    }

    @Override
    public int hashCode() {
        return 3425671;
    }

    @Override
    public String toString() {
        return "null";
    }
}
