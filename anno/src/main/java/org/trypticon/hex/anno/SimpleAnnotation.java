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

package org.trypticon.hex.anno;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.Value;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of a single annotation.
 *
 * @author trejkaz
 */
public class SimpleAnnotation implements Annotation {
    private long position;
    private long length;
    private Interpreter interpreter;
    private final Map<Attribute<?>, Object> attributes = new LinkedHashMap<>();

    public SimpleAnnotation(long position, long length, Interpreter interpreter) {
        if (interpreter == null) {
            throw new IllegalArgumentException("interpreter cannot be null");
        }

        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        this.position = position;
        this.length = length;
        this.interpreter = interpreter;
    }

    @Override
    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public long getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Interpreter<?> getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Value interpret(Binary binary) {
        return interpreter.interpret(binary, position, length);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Attribute<T> attribute) {
        return (T) attributes.get(attribute);
    }

    @Override
    public <T> void set(Attribute<T> attribute, T value) {
        if (value == null) {
            attributes.remove(attribute);
        } else {
            attributes.put(attribute, value);
        }
    }

    @Override
    public String toString() {
        return String.format("@%d..%d:%s(%s)", position, position + length - 1, interpreter, attributes);
    }
}
