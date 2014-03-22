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

import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.FixedLengthInterpreterInfo;
import org.trypticon.hex.interpreters.meta.SingleFixedLengthInterpreterInfo;
import org.trypticon.hex.util.LocalisedName;

/**
 * Convenience class holding a bunch of instances to date interpreters.
 *
 * @author trejkaz
 */
public class DateInterpreters {
    private DateInterpreters() {
    }

    public static final FixedLengthInterpreter<DateTime> C_TIME32_BE = new CTimeInterpreterBE32();
    public static final FixedLengthInterpreter<DateTime> C_TIME32_LE = new CTimeInterpreterLE32();
    public static final FixedLengthInterpreter<DateTime> C_TIME64_BE = new CTimeInterpreterBE64();
    public static final FixedLengthInterpreter<DateTime> C_TIME64_LE = new CTimeInterpreterLE64();
    public static final FixedLengthInterpreter<DateTime> JAVA_TIME = new JavaTimeInterpreter();
    public static final FixedLengthInterpreter<Date> DOS_DATE = new DosDateInterpreter();
    public static final FixedLengthInterpreter<Time> DOS_TIME = new DosTimeInterpreter();
    public static final FixedLengthInterpreter<DateTime> DOS_DATE_TIME = new DosDateTimeInterpreter();
    public static final FixedLengthInterpreter<DateTime> WINDOWS_FILE_TIME = new WindowsFileTimeInterpreter();
    public static final FixedLengthInterpreter<DateTime> WINDOWS_SYSTEM_TIME = new WindowsSystemTimeInterpreter();
    public static final FixedLengthInterpreter<DateTime> WINDOWS_OLE_DATE = new WindowsOleDateInterpreter();
    public static final FixedLengthInterpreter<DateTime> MAC_NS_DATE = new MacNSDateInterpreter();
    public static final FixedLengthInterpreter<DateTime> MAC_CLASSIC_DATE = new MacClassicDateInterpreter();
    public static final FixedLengthInterpreter<DateTime> NOTES_TIME_DATE = new NotesTimeDateInterpreter();

    public static final FixedLengthInterpreterInfo C_TIME32_BE_INFO = info("CTimeBE32", C_TIME32_BE);
    public static final FixedLengthInterpreterInfo C_TIME32_LE_INFO = info("CTimeLE32", C_TIME32_LE);
    public static final FixedLengthInterpreterInfo C_TIME64_BE_INFO = info("CTimeBE64", C_TIME64_BE);
    public static final FixedLengthInterpreterInfo C_TIME64_LE_INFO = info("CTimeLE64", C_TIME64_LE);
    public static final FixedLengthInterpreterInfo JAVA_TIME_INFO = info("JavaTime", JAVA_TIME);
    public static final FixedLengthInterpreterInfo DOS_DATE_INFO = info("DosDate", DOS_DATE);
    public static final FixedLengthInterpreterInfo DOS_TIME_INFO = info("DosTime", DOS_TIME);
    public static final FixedLengthInterpreterInfo DOS_DATE_TIME_INFO = info("DosDateTime", DOS_DATE_TIME);
    public static final FixedLengthInterpreterInfo WINDOWS_FILE_TIME_INFO = info("WindowsFileTime", WINDOWS_FILE_TIME);
    public static final FixedLengthInterpreterInfo WINDOWS_SYSTEM_TIME_INFO = info("WindowsSystemTime", WINDOWS_SYSTEM_TIME);
    public static final FixedLengthInterpreterInfo WINDOWS_OLE_DATE_INFO = info("WindowsOleDate", WINDOWS_OLE_DATE);
    public static final FixedLengthInterpreterInfo MAC_NS_DATE_INFO = info("MacNSDate", MAC_NS_DATE);
    public static final FixedLengthInterpreterInfo MAC_CLASSIC_DATE_INFO = info("MacClassicDate", MAC_CLASSIC_DATE);
    public static final FixedLengthInterpreterInfo NOTES_TIME_DATE_INFO = info("NotesTimeDate", NOTES_TIME_DATE);

    private static FixedLengthInterpreterInfo info(String name, FixedLengthInterpreter<?> interpreter) {
        return new SingleFixedLengthInterpreterInfo(
                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters." + name), interpreter);
    }
}
