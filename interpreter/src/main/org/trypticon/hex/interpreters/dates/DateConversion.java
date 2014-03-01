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

/**
 * Constants for date conversions.
 */
class DateConversion {
    static final int NANOS_IN_MILLISECOND = 1000000;

    static final int MILLIS_IN_SECOND = 1000;
    static final long NANOS_IN_SECOND = 1000000000L;

    static final long MILLIS_IN_DAY = 86400000L;
    static final long NANOS_IN_DAY = MILLIS_IN_DAY * 1000000;
}
