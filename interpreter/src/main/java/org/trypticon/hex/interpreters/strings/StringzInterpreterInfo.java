package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.LocalisedName;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Information about the null-terminated string interpreter.
 *
 * @author trejkaz
 */
public class StringzInterpreterInfo extends AbstractInterpreterInfo {
    public StringzInterpreterInfo() {
        super(new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Stringz"));
    }

    @Override
    public List<Option<?>> getOptions() {
        return Arrays.<Option<?>>asList(new Option<>("charset", String.class, true));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        String charset = (String) options.get("charset");
        return new StringzInterpreter(charset);
    }
}
