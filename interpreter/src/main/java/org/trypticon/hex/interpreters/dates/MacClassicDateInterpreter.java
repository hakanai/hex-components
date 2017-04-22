/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

/**
 * Interpreter for classic Mac OS (up to v9) date values.
 *
 * @author trejkaz
 */
public class MacClassicDateInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    // Computed using Calendar for January 1, 1904 00:00 UTC and then just taking the value.
    private static final long EPOCH = -2082844800000L;

    public MacClassicDateInterpreter() {
        super(DateTime.class, 4);
    }

    @Override
    protected DateTime interpret(Binary binary, long position) {
        // Value is the number of seconds since January 1, 1904 (UTC)
        long value = BigEndian.getUInt(binary, position);

        return new EpochDateTime(4, EPOCH, value * DateConversion.MILLIS_IN_SECOND, 0);
    }
}
