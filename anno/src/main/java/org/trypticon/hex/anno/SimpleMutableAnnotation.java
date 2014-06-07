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

/**
 * Implementation of a single annotation.
 *
 * @author trejkaz
 */
public class SimpleMutableAnnotation implements MutableAnnotation {
    private long position;
    private long length;
    private Interpreter interpreter;
    private String note;

    public SimpleMutableAnnotation(long position, long length, Interpreter interpreter, String note) {
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
        this.note = note;
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
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        if (note != null) {
            return String.format("@%d..%d:%s(%s)", position, position + length - 1, interpreter, note);
        } else {
            return String.format("@%d..%d:%s", position, position + length - 1, interpreter);
        }
    }
}
