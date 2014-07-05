package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractInterpreter;

/**
 * Interpreter for binary string values.
 *
 * @author trejkaz
 */
public class BinaryStringInterpreter extends AbstractInterpreter<BinaryStringValue> {
    public BinaryStringInterpreter() {
        super(BinaryStringValue.class);
    }

    @Override
    public BinaryStringValue interpret(Binary binary, long position, long length) {
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Strings cannot be longer than Integer.MAX_VALUE: " + length);
        }

        return new SimpleBinaryStringValue(binary.slice(position, length));
    }
}
