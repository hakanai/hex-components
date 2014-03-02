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

import org.trypticon.hex.interpreters.AbstractInterpreterStorage;
import org.trypticon.hex.interpreters.InterpreterInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Storage support for date interpreters.
 *
 * @author trejkaz
 */
public class DateInterpreterStorage extends AbstractInterpreterStorage {
    public DateInterpreterStorage() {
        register("c_time32_be", CTimeInterpreterBE32.class);
        register("c_time32_le", CTimeInterpreterLE32.class);
        register("c_time64_be", CTimeInterpreterBE64.class);
        register("c_time64_le", CTimeInterpreterLE64.class);
        register("java_time", JavaTimeInterpreter.class);
        register("dos_date", DosDateInterpreter.class);
        register("dos_time", DosTimeInterpreter.class);
        register("dos_date_time", DosDateTimeInterpreter.class);
        register("win_file_time", WindowsFileTimeInterpreter.class);
        register("win_system_time", WindowsSystemTimeInterpreter.class);
        register("win_ole_date", WindowsOleDateInterpreter.class);
        register("mac_osx_nsdate", MacNSDateInterpreter.class);
        register("mac_classic_date", MacClassicDateInterpreter.class);
        register("notes_time_date", NotesTimeDateInterpreter.class);
    }

    @Override
    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.<InterpreterInfo>asList(
                new CTimeInterpreterBE32Info(),
                new CTimeInterpreterLE32Info(),
                new CTimeInterpreterBE64Info(),
                new CTimeInterpreterLE64Info(),
                new JavaTimeInterpreterInfo(),
                new DosDateInterpreterInfo(),
                new DosTimeInterpreterInfo(),
                new DosDateTimeInterpreterInfo(),
                new WindowsFileTimeInterpreterInfo(),
                new WindowsSystemTimeInterpreterInfo(),
                new WindowsOleDateInterpreterInfo(),
                new MacNSDateInterpreterInfo(),
                new MacClassicDateInterpreterInfo(),
                new NotesTimeDateInterpreterInfo());
    }

}
