/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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

import java.util.Map;

/**
 * An interpreter info which knows its interpreter will be fixed length.
 */
public interface FixedLengthInterpreterInfo extends InterpreterInfo {
    /**
     * Gets the length of values returned from the interpreter.
     *
     * @return the length of values returned from the interpreter.
     */
    long getValueLength();

    @Override
    FixedLengthInterpreter<?> create(Map<String, Object> options);

}
