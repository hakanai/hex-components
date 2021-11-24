/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.anno;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

/**
 * Null implementation of an annotation.
 *
 * Does nothing, says nothing.
 */
public class NullAnnotation implements Annotation {

    @Override
    public long getPosition() {
        return 0;
    }

    @Override
    public long getLength() {
        return 1;
    }

    @Nonnull
    @Override
    public Interpreter<?> getInterpreter() {
        return new NullInterpreter();
    }

    @Nullable
    @Override
    public <T> T get(@Nonnull Attribute<T> attribute) {
        return null;
    }

    @Override
    public <T> void set(@Nonnull Attribute<T> attribute, @Nullable T value) {}

    @Override
    public <T> void setIfNotNull(@Nonnull Attribute<T> attribute, @Nullable T value) {}
}
