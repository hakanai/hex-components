package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractInterpreter;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Interpreter for null-terminated string values.
 *
 * @author trejkaz
 */
public class StringzInterpreter extends AbstractInterpreter<StringValue> {
    private final Charset charset;

    @Deprecated
    public StringzInterpreter(String charset) {
        this(Charset.forName(charset));
    }

    public StringzInterpreter(Charset charset) {
        super(StringValue.class);
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    @Override
    public StringValue interpret(Binary binary, long position, long length) {
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Strings cannot be longer than Integer.MAX_VALUE: " + length);
        }

        ByteBuffer buffer = ByteBuffer.allocate((int) length);
        binary.read(position, buffer);
        buffer.rewind();

        //TODO: Open question about whether stringz should support 2-byte charsets.
        while (buffer.hasRemaining()) {
            if (buffer.get() == 0) {
                buffer.position(buffer.position() - 1);
                break;
            }
        }
        buffer.flip(); // limit() is now at the 0, so the decode won't include it.

        CharBuffer charBuffer = charset.decode(buffer);

        return new SimpleStringValue(charBuffer.toString(), length);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof StringzInterpreter && charset.equals(((StringzInterpreter) o).charset);
    }

    @Override
    public int hashCode() {
        return 234613 ^ charset.hashCode();
    }

    @Override
    public String toString() {
        return String.format("stringz(%s)", charset.name());
    }
}
