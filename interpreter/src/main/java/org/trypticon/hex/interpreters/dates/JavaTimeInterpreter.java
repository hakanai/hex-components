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

package org.trypticon.hex.interpreters.dates;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.interpreters.primitives.BigEndian;

import javax.annotation.Nonnull;

/**
 * Interpreter for Java time values.
 *
 * @author trejkaz
 */
public class JavaTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    public JavaTimeInterpreter() {
        super("java_time", DateTime.class, 8);
    }

    @Nonnull
    @Override
    protected DateTime interpret(@Nonnull Binary binary, long position) {
        // Value is the number of milliseconds since January 1, 1970 (UTC)
        long value = BigEndian.getLong(binary, position);

        return new EpochDateTime(8, 0, value, 0);
    }
}
