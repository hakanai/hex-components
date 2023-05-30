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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * Utility working around Java's continued lack of a proper API for cleaning up
 * mapped byte buffers. Supports Java 9+ as far as I can tell, but we only support
 * Java 11+ so that is OK for now.
 *
 * @author trejkaz
 */
class ByteBufferUnmapper {
    private static final Unmapper unmapper = initUnmapper();

    private static Unmapper initUnmapper() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            MethodHandle cleanerMethod = MethodHandles.lookup().findVirtual(
                unsafeClass, "invokeCleaner", MethodType.methodType(void.class, ByteBuffer.class));

            // Works around `getUnsafe()` checking the caller.
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object theUnsafe = f.get(null);
            MethodHandle boundCleanerMethod = cleanerMethod.bindTo(theUnsafe);

            return buffer -> {
                if (!buffer.isDirect()) {
                    throw new IllegalArgumentException("unmapping only works with direct buffers");
                }
                try {
                    boundCleanerMethod.invokeExact(buffer);
                } catch (Throwable t) {
                    throw new UncheckedIOException(new IOException("Unable to unmap the mapped buffer", t));
                }
              };

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                 NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Could not initialise byte buffer unmapper", e);
        }
    }

    static void unmap(ByteBuffer buffer) {
        unmapper.unmap(buffer);
    }

    private interface Unmapper {
        void unmap(ByteBuffer buffer);
    }
}
