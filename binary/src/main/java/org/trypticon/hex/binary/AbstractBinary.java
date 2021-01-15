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

import java.nio.ByteBuffer;

/**
 * Base class for new binary implementations.
 *
 * @author trejkaz
 */
public abstract class AbstractBinary implements Binary {

    @Override
    public final byte read(long position) {
        if (position < 0 || position >= length()) {
            throw new IndexOutOfBoundsException(
                    String.format("Position %d is out of bounds (0..%d)", position, length() - 1));
        }

        return readSpi(position);
    }

    /**
     * Called to read a single byte at the given position.
     * The position will have already been checked before calling this method.
     *
     * @param position the position.
     * @return the byte at that position.
     */
    protected abstract byte readSpi(long position);

    @Override
    public final void read(long position, ByteBuffer buffer) {
        if (position < 0 || position + buffer.remaining() > length()) {
            throw new IndexOutOfBoundsException(
                    String.format("Range %d..%d is out of bounds (0..%d)", position, position + buffer.remaining() - 1,
                                  length() - 1));
        }

        readSpi(position, buffer);
    }

    /**
     * Called to read multiple bytes at the given position.
     * The position and length will have already been checked before calling this method.
     *
     * @param position the position.
     * @param buffer the buffer to read into.
     */
    protected abstract void readSpi(long position, ByteBuffer buffer);

    @Override
    public void read(long position, byte[] buffer) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer, 0, buffer.length);
        read(position, wrapped);
    }

    @Override
    public void read(long position, byte[] buffer, int offset, int length) {
        ByteBuffer wrapped = ByteBuffer.wrap(buffer, offset, length);
        read(position, wrapped);
    }

    @Override
    public final Binary slice(final long position, final long length) {
        return new AbstractBinary() {
            @Override
            protected byte readSpi(long position2) {
                return AbstractBinary.this.read(position + position2);
            }

            @Override
            protected void readSpi(long position2, ByteBuffer buffer) {
                AbstractBinary.this.read(position + position2, buffer);
            }

            @Override
            public long length() {
                return length;
            }
        };
    }

    @Override
    public void close() {
    }
}
