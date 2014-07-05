package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.LocalisedName;

import java.util.Map;

/**
 * Information about the null-terminated binary string interpreter.
 *
 * @author trejkaz
 */
public class BinaryStringzInterpreterInfo extends AbstractInterpreterInfo {
    public BinaryStringzInterpreterInfo() {
        super(new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.BinaryStringz"));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        return new BinaryStringzInterpreter();
    }
}
