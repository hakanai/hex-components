/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
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

import org.trypticon.hex.binary.Binary;

import javax.annotation.Nonnull;

/**
 * Interface for interpretation of a sequence of binary as some typed value.
 *
 * @param <V> the value type.
 * @author trejkaz
 */
public interface Interpreter<V extends Value> {

    /**
     * Gets the type of the interpreted values.
     *
     * @return the type of the interpreted values.
     */
    Class<V> getType();

    /**
     * Interprets the value for a range.
     *
     * @param binary the binary.
     * @param position the position of the start of the value.
     * @param length the length of the value to interpret.
     * @return the value.
     */
    @Nonnull
    V interpret(@Nonnull Binary binary, long position, long length);
}
