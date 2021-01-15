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

package org.trypticon.hex.anno;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface marking an annotation within the binary.
 *
 * @author trejkaz
 */
public interface Annotation {

    /**
     * Gets the position in the binary of the first byte of the annotation.
     *
     * @return the position in the binary.
     */
    long getPosition();

    /**
     * Gets the length of the annotation.
     *
     * @return the length of the annotation.
     */
    long getLength();

    /**
     * Gets the interpreter used to interpret the value at the given position.
     *
     * @return the interpreter.
     */
    @Nonnull
    Interpreter<?> getInterpreter();

    /**
     * Convenience method to call the interpreter with the appropriate position and length
     * to interpret the value.
     *
     * @param binary the binary.
     * @return the value.
     */
    @Nonnull
    Value interpret(@Nonnull Binary binary);

    /**
     * Gets the value of a custom attribute.
     *
     * @param attribute the custom attribute.
     * @param <T> the attribute type.
     * @return the attribute value.
     */
    @Nullable
    <T> T get(@Nonnull Attribute<T> attribute);

    /**
     * Sets the value of a custom attribute.
     *
     * @param attribute the custom attribute.
     * @param value the attribute value.
     * @param <T> the attribute type.
     */
    <T> void set(@Nonnull Attribute<T> attribute, T value);

}
