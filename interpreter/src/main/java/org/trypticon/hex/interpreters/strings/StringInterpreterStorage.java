/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
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

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.InterpreterInfo;
import org.trypticon.hex.interpreters.InterpreterStorage;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage support for string interpreters.
 *
 * @author trejkaz
 */
public class StringInterpreterStorage implements InterpreterStorage {
    @Override
    public List<InterpreterInfo> getGroupedInterpreterInfos() {
        return getInterpreterInfos();
    }

    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.<InterpreterInfo>asList(
                new StringInterpreterInfo(), new StringzInterpreterInfo(),
                new BinaryStringInterpreterInfo(), new BinaryStringzInterpreterInfo());
    }

    @Override
    public Map<String, Object> toMap(Interpreter interpreter) {
        if (interpreter instanceof StringInterpreter) {
            Map<String, Object> result = new LinkedHashMap<>(1);
            result.put("name", "string");
            result.put("charset", ((StringInterpreter) interpreter).getCharset());
            return result;
        } else if (interpreter instanceof StringzInterpreter) {
            Map<String, Object> result = new LinkedHashMap<>(1);
            result.put("name", "string");
            result.put("charset", ((StringzInterpreter) interpreter).getCharset());
            return result;
        } else if (interpreter instanceof BinaryStringInterpreter) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("name", "binstring");
            return result;
        } else if (interpreter instanceof BinaryStringzInterpreter) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("name", "binstringz");
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Interpreter fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        switch (name) {
            case "string": {
                String charset = (String) map.get("charset");
                return new StringInterpreter(charset);
            }
            case "stringz": {
                String charset = (String) map.get("charset");
                return new StringzInterpreter(charset);
            }
            case "binstring":
                return new BinaryStringInterpreter();
            case "binstringz":
                return new BinaryStringzInterpreter();
            default:
                return null;
        }
    }
}
