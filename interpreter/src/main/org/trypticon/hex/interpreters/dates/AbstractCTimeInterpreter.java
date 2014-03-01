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

package org.trypticon.hex.interpreters.dates;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;

/**
 * Common code for all C time interpreters.
 */
abstract class AbstractCTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {

    protected AbstractCTimeInterpreter(int length) {
        super(DateTime.class, length);
    }

    @Override
    protected final DateTime interpret(Binary binary, long position) {
        // Value is the number of seconds intervals since January 1, 1970 (UTC)
        long value = getValue(binary, position);

        return new EpochDateTime((int) getValueLength(), 0, value * 1000, 0);
    }

    /**
     * Gets the time value (seconds since 1970.)
     *
     * @param binary the binary.
     * @param position the position in the stream.
     * @return the time value.
     */
    protected abstract long getValue(Binary binary, long position);
}
