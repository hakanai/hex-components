/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2013  Trejkaz, Hex Project
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.InterpreterInfo;

/**
 * Information about the string interpreter.
 *
 * @author trejkaz
 */
public class StringInterpreterInfo implements InterpreterInfo {
    @Override
    public String getHumanName() {
        return "String";
    }

    @Override
    public List<Option> getOptions() {
        return Arrays.asList(new Option("charset", String.class, true));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        String charset = (String) options.get("charset");
        return new StringInterpreter(charset);
    }
}
