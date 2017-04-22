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

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;

/**
 * Binary which maps a file into memory.
 *
 * @author trejkaz
 */
class MemoryMappedFileBinary extends AbstractBinary implements Binary, Closeable {

    /**
     * Delegate binary implementation.
     */
    private Binary delegate;

    /**
     * Constructs the binary, mapping the provided file into memory.
     *
     * @param file the file to map into memory.
     * @throws IOException if the file could not be read.
     */
    public MemoryMappedFileBinary(Path file) throws IOException {
        try (FileChannel channel = FileChannel.open(file)) {
            ByteBuffer mapped = channel.map(MapMode.READ_ONLY, 0, channel.size());
            delegate = new ByteBufferBinary(mapped);
        }
    }

    @Override
    public long length() {
        return delegate.length();
    }

    @Override
    protected byte readSpi(long position) {
        return delegate.read(position);
    }

    @Override
    protected void readSpi(long position, ByteBuffer buffer) {
        delegate.read(position, buffer);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
