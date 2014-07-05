package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractValue;
import org.trypticon.hex.util.Format;

import java.util.Locale;

/**
 * Simple implementation of a binary string value.
 *
 * @author trejkaz
 */
public class SimpleBinaryStringValue extends AbstractValue implements BinaryStringValue {
    private final Binary binary;

    public SimpleBinaryStringValue(Binary binary) {
        this.binary = binary;
    }

    @Override
    public long length() {
        return binary.length();
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return new StringInterpreter("UTF-8")
                .interpret(binary, 0, binary.length())
                .toLocalisedString(style, locale);
    }
}
