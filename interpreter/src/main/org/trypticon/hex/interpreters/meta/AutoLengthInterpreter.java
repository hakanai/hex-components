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

package org.trypticon.hex.interpreters.meta;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>An interpreter containing multiple interpreters where each has a different length.</p>
 *
 * <p>The appropriate interpreter will automatically be selected based on the length of
 *    the value being interpreted.</p>
 */
public class AutoLengthInterpreter implements Interpreter<Value> {
    private final Map<Long, Interpreter<?>> interpretersByLength;

    public AutoLengthInterpreter(FixedLengthInterpreter<?>... interpretersByLength) {
        this.interpretersByLength = new HashMap<>(interpretersByLength.length);
        for (FixedLengthInterpreter<?> interpreter : interpretersByLength) {
            this.interpretersByLength.put(interpreter.getValueLength(), interpreter);
        }
    }

    @Override
    public Class<Value> getType() {
        return Value.class; // can't be any more specific unless there happened to be a common superclass to them.
    }

    @Override
    public Value interpret(Binary binary, long position, long length) {
        Interpreter<?> interpreter = interpretersByLength.get(length);
        if (interpreter != null) {
            return interpreter.interpret(binary, position, length);
        } else {
            throw new IllegalArgumentException("Only supports values of lengths " + interpretersByLength.keySet()
                                               + " but got " + length);
        }
    }
}
