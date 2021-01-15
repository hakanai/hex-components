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

package org.trypticon.hex.interpreters.meta;

import org.trypticon.hex.interpreters.AbstractInterpreterInfo;
import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.FixedLengthInterpreterInfo;
import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.interpreters.options.ByteOrderOption;
import org.trypticon.hex.util.Localisable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>A meta-interpreter giving you the choice of byte order in the options,
 *    thus allowing selecting between two different interpreters.</p>
 *
 * <p>This should be useful for simpler GUIs where you get the option to toggle a byte order flag.</p>
 *
 * @param <V> the type of value which is interpreted.
 */
public class ByteOrderOptionInterpreterInfo<V extends Value> extends AbstractInterpreterInfo implements FixedLengthInterpreterInfo {
    private final FixedLengthInterpreter<V> bigEndianInterpreter;
    private final FixedLengthInterpreter<V> littleEndianInterpreter;

    public ByteOrderOptionInterpreterInfo(Localisable name,
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
    public List<Option<?>> getOptions() {
        return Collections.<Option<?>>singletonList(new Option<>("byteOrder", ByteOrderOption.class, true));
    }

    @Override
    public FixedLengthInterpreter<?> create(Map<String, Object> options) {
        ByteOrderOption byteOrder = (ByteOrderOption) options.get("byteOrder");
        if (byteOrder == null) {
            throw new IllegalArgumentException("Options do not contain required option: byteOrder");
        }

        return byteOrder == ByteOrderOption.BIG_ENDIAN ? bigEndianInterpreter : littleEndianInterpreter;
    }
}
