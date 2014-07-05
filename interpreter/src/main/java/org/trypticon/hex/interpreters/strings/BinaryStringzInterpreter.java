package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractInterpreter;

/**
 * Interpreter for null-terminated binary string values.
 *
 * @author trejkaz
 */
public class BinaryStringzInterpreter extends AbstractInterpreter<BinaryStringValue> {
    public BinaryStringzInterpreter() {
        super(BinaryStringValue.class);
    }

    @Override
    public BinaryStringValue interpret(Binary binary, long position, long length) {
        if (length > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Strings cannot be longer than Integer.MAX_VALUE: " + length);
        }

        long actualLength = 0;
        while (actualLength < length && binary.read(actualLength) != 0) {
            actualLength++;
        }

        return new SimpleBinaryStringValue(binary.slice(position, actualLength));
    }
}
