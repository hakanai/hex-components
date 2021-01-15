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

package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractInterpreter;

import javax.annotation.Nonnull;

/**
 * Interpreter for binary string values.
 *
 * @author trejkaz
 */
public class BinaryStringInterpreter extends AbstractInterpreter<BinaryStringValue> {
    public BinaryStringInterpreter() {
        super("binstring", BinaryStringValue.class);
    }

    @Nonnull
    @Override
    public BinaryStringValue interpret(@Nonnull Binary binary, long position, long length) {
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Strings cannot be longer than Integer.MAX_VALUE: " + length);
        }

        return new SimpleBinaryStringValue(binary.slice(position, length));
    }
}
