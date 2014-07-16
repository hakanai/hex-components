package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.LocalisedName;

import java.nio.charset.Charset;
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
        return Arrays.<Option<?>>asList(new Option<>("charset", Charset.class, true));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        Charset charset = (Charset) options.get("charset");
        return new StringzInterpreter(charset);
    }
}
