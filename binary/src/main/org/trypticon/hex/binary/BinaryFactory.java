/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

import org.trypticon.hex.util.URLUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Factory for creating binary implementations for common things.
 *
 * @author trejkaz
 */
public class BinaryFactory {

    private BinaryFactory() {
    }

    /**
     * Wraps a byte buffer, returning it as a binary.
     *
     * @param buffer the byte buffer.
     * @return the binary.
     */
    public static Binary wrap(ByteBuffer buffer) {
        return new ByteBufferBinary(buffer);
    }

    /**
     * Wraps a byte array, returning it as a binary.
     *
     * @param array the byte array.
     * @return the binary.
     */
    public static Binary wrap(byte[] array) {
        return new ByteBufferBinary(ByteBuffer.wrap(array));
    }

    /**
     * Opens a file, returning it as a binary.
     *
     * @param file the file.
     * @return the binary.
     * @throws IOException if the file could not be opened for reading.
     */
    public static Binary open(Path file) throws IOException {
        if (Files.size(file) < Integer.MAX_VALUE) {
            return new MemoryMappedFileBinary(file);
        } else {
            return new FileChannelBinary(file);
        }
    }

    /**
     * Opens a URL, returning the content as a binary.  If the URL happens
     * to be a {@code file} URL then it will memory map the file.  Otherwise
     * it will read the entire stream into memory and expose that.
     *
     * @param location the location of the binary.
     * @return the binary.
     * @throws IOException if an error occurs opening the URL.
     */
    public static Binary open(URL location) throws IOException {
        if ("file".equals(location.getProtocol())) {
            return open(URLUtils.toPath(location));
        } else {
            // TODO: This could be improved to load in the background.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream in = location.openStream()) {
                byte[] buf = new byte[16 * 1024];
                int bytesRead;
                while ((bytesRead = in.read(buf)) != -1) {
                    baos.write(buf, 0, bytesRead);
                }
            }
            return wrap(baos.toByteArray());
        }
    }
}
