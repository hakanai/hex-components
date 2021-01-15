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

package org.trypticon.hex.binary;

import java.lang.IndexOutOfBoundsException;
import java.nio.ByteBuffer;
import java.io.Closeable;

/**
 * Generic interface to things which contain binary data.
 *
 * @author trejkaz
 */
public interface Binary extends Closeable {

    /**
     * Gets the length of the binary, in bytes.
     *
     * @return the length of the binary, in bytes.
     */
    long length();

    /**
     * Reads a single byte from a given position.
     *
     * @param position the position to read.
     * @return the single byte.
     * @throws IndexOutOfBoundsException if the position is out of bounds.
     */
    byte read(long position);

    /**
     * Reads bytes from a given position into the buffer provided, filling the entire buffer.
     *
     * @param position tne position to begin reading from.
     * @param buffer the buffer to read into.
     * @param offset the offset in the buffer to begin reading into.
     * @param length the number of bytes to read.
     */
    void read(long position, byte[] buffer, int offset, int length);

    /**
     * Reads bytes from a given position into the buffer provided, filling the entire buffer.
     *
     * @param position tne position to begin reading from.
     * @param buffer the buffer to read into.
     */
    void read(long position, byte[] buffer);

    /**
     * Reads bytes from a given position into the buffer provided.
     *
     * @param position the position to begin reading from.
     * @param buffer the buffer to read into.
     */
    void read(long position, ByteBuffer buffer);

    /**
     * Gets a view of one slice of the binary.
     *
     * @param position the position to start at.
     * @param length the length to return.
     * @return the slice of the binary.
     */
    Binary slice(long position, long length);

    /**
     * Closes the binary.  Other methods SHOULD NOT be used after closing.
     */
    @Override
    void close();
}
