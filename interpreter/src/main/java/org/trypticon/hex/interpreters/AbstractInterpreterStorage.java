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

package org.trypticon.hex.interpreters;

import java.util.HashMap;
import java.util.Map;

/**
 * Base convenience class for implementing interpreter storage.
 *
 * @author trejkaz
 */
public abstract class AbstractInterpreterStorage implements InterpreterStorage {
    private final Map<Class<? extends Interpreter>, String> classToName =
            new HashMap<>(10);
    private final Map<String, Class<? extends Interpreter>> nameToClass =
            new HashMap<>(10);

    protected AbstractInterpreterStorage() {
    }

    protected void register(String name, Class<? extends Interpreter> klass) {
        classToName.put(klass, name);
        nameToClass.put(name, klass);
    }

    @Override
    public Map<String, Object> toMap(Interpreter interpreter) {
        String name = classToName.get(interpreter.getClass());
        if (name != null) {
            Map<String, Object> result = new HashMap<>(1);
            result.put("name", name);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public Interpreter fromMap(Map<String, Object> map) {
        String name = (String) map.get("name");
        Class<? extends Interpreter> klass = nameToClass.get(name);
        if (klass != null) {
            try {
                return klass.newInstance();
            } catch (InstantiationException e) {
                throw new IllegalStateException("Constructor should have been no-op", e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Constructor should have been accessible", e);
            }
        } else {
            return null;
        }
    }
}
