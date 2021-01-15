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

package org.trypticon.hex.interpreters.dates;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.primitives.BigEndian;

/**
 * Interpreter for 64-bit Big Endian C {@code time_t} values.
 *
 * @author trejkaz
 */
public class CTimeInterpreterBE64 extends AbstractCTimeInterpreter {

    public CTimeInterpreterBE64() {
        super("c_time64_be", 8);
    }

    @Override
    protected long getValue(Binary binary, long position) {
        return BigEndian.getLong(binary, position);
    }
}
