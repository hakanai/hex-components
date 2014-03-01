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

package org.trypticon.hex.interpreters.primitives;

import org.trypticon.hex.binary.Binary;

/**
 * Little Endian utilities.
 *
 * @author trejkaz
 */
public class LittleEndian {
    private LittleEndian() {
    }

    public static short getShort(Binary binary, long position) {
        return Primitives.getShort(binary.read(position + 1),
                                   binary.read(position));
    }

    public static int getUShort(Binary binary, long position) {
        return Primitives.getUShort(binary.read(position + 1),
                                    binary.read(position));
    }

    public static int getInt(Binary binary, long position) {
        return Primitives.getInt(binary.read(position + 3),
                                 binary.read(position + 2),
                                 binary.read(position + 1),
                                 binary.read(position));
    }

    public static long getLong(Binary binary, long position) {
        return Primitives.getLong(binary.read(position + 7),
                                  binary.read(position + 6),
                                  binary.read(position + 5),
                                  binary.read(position + 4),
                                  binary.read(position + 3),
                                  binary.read(position + 2),
                                  binary.read(position + 1),
                                  binary.read(position));
    }
}
