/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.nulls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.InterpreterInfo;
import org.trypticon.hex.interpreters.InterpreterStorage;

/**
 * Interpreter storage for null values.
 *
 * @author trejkaz
 */
public class NullInterpreterStorage implements InterpreterStorage {
    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.asList((InterpreterInfo) new NullInterpreterInfo());
    }

    @Override
    public Map<String, Object> toMap(Interpreter interpreter) {
        if (interpreter.getClass() == NullInterpreter.class) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("name", "null");
            return map;
        } else {
            return null;
        }
    }

    @Override
    public Interpreter fromMap(Map<String, Object> map) {
        if ("null".equals(map.get("name"))) {
            return new NullInterpreter();
        } else {
            return null;
        }
    }
}
