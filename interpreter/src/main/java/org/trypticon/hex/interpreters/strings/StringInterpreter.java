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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Interpreter for string values.
 *
 * @author trejkaz
 */
public class StringInterpreter extends AbstractInterpreter<StringValue> {
    private final Charset charset;

    public StringInterpreter(Charset charset) {
        super(String.format("string(%s)", charset.name()), StringValue.class);
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    @Nonnull
    @Override
    public StringValue interpret(@Nonnull Binary binary, long position, long length) {
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Strings cannot be longer than Integer.MAX_VALUE: " + length);
        }

        ByteBuffer buffer = ByteBuffer.allocate((int) length);
        binary.read(position, buffer);
        buffer.rewind();

        CharBuffer charBuffer = charset.decode(buffer);
        return new SimpleStringValue(charBuffer.toString(), length);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof StringInterpreter && charset.equals(((StringInterpreter) o).charset);
    }

    @Override
    public int hashCode() {
        return 234611 ^ charset.hashCode();
    }
}
