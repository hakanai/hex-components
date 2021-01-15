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

import org.trypticon.hex.interpreters.AbstractFixedLengthInterpreter;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.binary.Binary;

import javax.annotation.Nonnull;

/**
 * Interprets a pair of DOS date and time.
 *
 * @author trejkaz
 */
public class DosDateTimeInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    private final Interpreter<Date> dateInterpreter = new DosDateInterpreter();
    private final Interpreter<Time> timeInterpreter = new DosTimeInterpreter();

    public DosDateTimeInterpreter() {
        super("dos_date_time", DateTime.class, 4);
    }

    @Nonnull
    @Override
    public DateTime interpret(@Nonnull Binary binary, long position) {
        return new SimpleDateTime(dateInterpreter.interpret(binary, position + 2, 2),
                                  timeInterpreter.interpret(binary, position, 2));
    }
}
