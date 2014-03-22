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

package org.trypticon.hex.interpreters.meta;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.FixedLengthInterpreterInfo;
import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.util.Localisable;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>A meta-interpreter giving you the choice of big or little endian in the options,
 *    thus allowing selecting between two different interpreters.</p>
 *
 * <p>This should be useful for simpler GUIs where you get the option to toggle an endian-ness flag.</p>
 *
 * @param <V> the type of value which is interpreted.
 */
public class EndianOptionInterpreterInfo<V extends Value> extends AbstractInterpreterInfo implements FixedLengthInterpreterInfo {
    private final FixedLengthInterpreter<V> bigEndianInterpreter;
    private final FixedLengthInterpreter<V> littleEndianInterpreter;

    public EndianOptionInterpreterInfo(Localisable name,
                                       FixedLengthInterpreter<V> bigEndianInterpreter,
                                       FixedLengthInterpreter<V> littleEndianInterpreter)
    {
        super(name);
        this.bigEndianInterpreter = bigEndianInterpreter;
        this.littleEndianInterpreter = littleEndianInterpreter;
    }

    @Override
    public long getValueLength() {
        return bigEndianInterpreter.getValueLength();
    }

    @Override
    public List<Option> getOptions() {
        return Arrays.asList(new Option("byteOrder", ByteOrder.class, true));
    }

    @Override
    public FixedLengthInterpreter<?> create(Map<String, Object> options) {
        ByteOrder byteOrder = (ByteOrder) options.get("byteOrder");
        if (byteOrder == null) {
            throw new IllegalArgumentException("Options do not contain required option: byteOrder");
        }

        return byteOrder == ByteOrder.BIG_ENDIAN ? bigEndianInterpreter : littleEndianInterpreter;
    }
}
