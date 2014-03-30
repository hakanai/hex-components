/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
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
import org.trypticon.hex.interpreters.meta.AutoLengthInterpreterInfo;
import org.trypticon.hex.interpreters.meta.ByteOrderOptionInterpreterInfo;
import org.trypticon.hex.util.LocalisedName;

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
    public List<InterpreterInfo> getGroupedInterpreterInfos() {
        return Arrays.asList(
                new AutoLengthInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedCTime"),
                        new ByteOrderOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.CTime32"),
                                DateInterpreters.C_TIME32_BE,
                                DateInterpreters.C_TIME32_LE),
                        new ByteOrderOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.CTime64"),
                                DateInterpreters.C_TIME64_BE,
                                DateInterpreters.C_TIME64_LE)),
                DateInterpreters.JAVA_TIME_INFO,
                DateInterpreters.DOS_DATE_INFO,
                DateInterpreters.DOS_TIME_INFO,
                DateInterpreters.DOS_DATE_TIME_INFO,
                DateInterpreters.WINDOWS_FILE_TIME_INFO,
                DateInterpreters.WINDOWS_SYSTEM_TIME_INFO,
                DateInterpreters.WINDOWS_OLE_DATE_INFO,
                DateInterpreters.MAC_NS_DATE_INFO,
                DateInterpreters.MAC_CLASSIC_DATE_INFO,
                DateInterpreters.NOTES_TIME_DATE_INFO);
    }

    @Override
    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.<InterpreterInfo>asList(
                DateInterpreters.C_TIME32_BE_INFO,
                DateInterpreters.C_TIME32_LE_INFO,
                DateInterpreters.C_TIME64_BE_INFO,
                DateInterpreters.C_TIME64_LE_INFO,
                DateInterpreters.JAVA_TIME_INFO,
                DateInterpreters.DOS_DATE_INFO,
                DateInterpreters.DOS_TIME_INFO,
                DateInterpreters.DOS_DATE_TIME_INFO,
                DateInterpreters.WINDOWS_FILE_TIME_INFO,
                DateInterpreters.WINDOWS_SYSTEM_TIME_INFO,
                DateInterpreters.WINDOWS_OLE_DATE_INFO,
                DateInterpreters.MAC_NS_DATE_INFO,
                DateInterpreters.MAC_CLASSIC_DATE_INFO,
                DateInterpreters.NOTES_TIME_DATE_INFO);
    }

}
