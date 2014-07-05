package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.LocalisedName;

import java.util.Map;

/**
 * Information about the binary string interpreter.
 *
 * @author trejkaz
 */
public class BinaryStringInterpreterInfo extends AbstractInterpreterInfo {
    public BinaryStringInterpreterInfo() {
        super(new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.BinaryString"));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        return new BinaryStringInterpreter();
    }
}
