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
import java.nio.file.Path;

/**
 * Binary which uses a file channel to access the data on demand.
 *
 * @author trejkaz
 */
public class FileChannelBinary extends AbstractBinary implements Binary, Closeable {

    /**
     * The file channel.
     */
    private final FileChannel channel;

    /**
     * The length of the binary.
     */
    private final long length;

    /**
     * Constructs the binary by opening the given file.
     *
     * @param file the file to open.
     * @throws IOException if an error occurs reading from the file.
     */
    public FileChannelBinary(Path file) throws IOException {
        channel = FileChannel.open(file);
        length = channel.size();
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    protected byte readSpi(long position) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        try {
            channel.read(buffer, position);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file", e);
        }
        buffer.flip();
        return buffer.get();
    }

    @Override
    protected void readSpi(long position, ByteBuffer buffer) {
        try {
            while (buffer.hasRemaining()) {
                int read = channel.read(buffer, position);
                position += read;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file", e);
        }
    }
}
