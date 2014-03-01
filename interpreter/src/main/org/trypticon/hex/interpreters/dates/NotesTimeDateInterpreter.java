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
import org.trypticon.hex.interpreters.primitives.LittleEndian;

/**
 * Interpreter for Lotus Notes {@code TIMEDATE} values.
 *
 * @author trejkaz
 */
public class NotesTimeDateInterpreter extends AbstractFixedLengthInterpreter<DateTime> {
    // Computed using Calendar for January 1, 4713 BC 00:00 UTC and then just taking the value.
    private static final long EPOCH = -210866803200000L;
    private static final long MILLIS_IN_DAY = 86400000L;

    public NotesTimeDateInterpreter() {
        super(DateTime.class, 8);
    }

    @Override
    protected DateTime interpret(Binary binary, long position) {
        // typedef struct tagTIMEDATE {
        //    DWORD Innards[2];
        // } TIMEDATE;

        int timeValue = LittleEndian.getInt(binary, position);
        int dateValue = LittleEndian.getInt(binary, position + 4);

        // "The first DWORD, Innards[0], contains the number of hundredths of seconds since midnight,
        //  Greenwich mean time.
        //  The date and the time zone and Daylight Savings Time settings are encoded in Innards[1].
        //  The 24 low-order bits contain the Julian Day, the number of days since January 1, 4713 BC.
        //      Note that this is NOT the same as the Julian calendar! The Julian Day was originally devised as an
        //      aid to astronomers. Since only days are counted, weeks, months, and years are ignored in calculations.
        //      The Julian Day is defined to begin at noon; for simplicity, Notes assumes that the day begins at
        //      midnight.
        //  The high-order byte, bits 31-24, encodes the time zone and Daylight Savings Time information.
        //      The high-order bit, bit 31 (0x80000000), is set if Daylight Savings Time is observed.
        //      Bit 30 (0x40000000) is set if the time zone is east of Greenwich mean time.
        //      Bits 27-24 contain the number of hours difference between the time zone and Greenwich mean time
        //      and bits 29-28 contain the number of 15-minute intervals in the difference."

        long millis = (0xFFFFFF & dateValue) * MILLIS_IN_DAY + timeValue * 10;

        return new EpochDateTime(8, EPOCH, millis, 0);
    }
}
