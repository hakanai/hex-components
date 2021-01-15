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

package org.trypticon.hex.binary;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Binary which wraps a byte buffer.
 *
 * @author trejkaz
 */
class ByteBufferBinary extends AbstractBinary {

    /**
     * The wrapped byte buffer.
     */
    private final ByteBuffer buffer;

    /**
     * Will be set to {@code true} on {@code close()}.
     */
    private volatile boolean closed = false;

    /**
     * A lock allowing multiple reading threads to get in at the same time, giving {@code close()} a way to determine
     * when all reads have finished so that it can set the {@code closed} flag and clean the buffer while knowing that a
     * read can't be half-completed.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Constructs binary wrapping a byte buffer.
     *
     * @param buffer the wrapped byte buffer.
     */
    public ByteBufferBinary(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public long length() {
        return buffer.capacity();
    }

    @Override
    protected byte readSpi(long position) {
        lock.readLock().lock();
        try {
            throwIfClosed();
            return buffer.get((int) position);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    protected void readSpi(long position, ByteBuffer buffer) {
        lock.readLock().lock();
        try {
            throwIfClosed();

            ByteBuffer dup = this.buffer.duplicate();
            dup.position((int) position);
            dup.limit(buffer.limit() + (int) position);
            buffer.put(dup);
        } finally {
            lock.readLock().unlock();
        }
    }

    private void throwIfClosed() {
        if (closed) {
            throw new IllegalStateException("close() has already been called");
        }
    }

    @Override
    public void close() {
        lock.writeLock().lock();
        try {
            closed = true;

            // Doing this kind of clean is normally dangerous because if the caller
            // has access to the buffer, it will cause the entire VM to segfault.
            // This is why we jump through hoops to lock it and make sure other
            // threads can't be using it at the same time.
            if (buffer.isDirect()) {
                ByteBufferUnmapper.unmap(buffer);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
