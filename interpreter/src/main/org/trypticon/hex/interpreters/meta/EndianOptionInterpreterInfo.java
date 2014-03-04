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
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.util.Name;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>A meta-interpreter giving you the choice of big or little endian in the options,
 *    thus allowing selecting between two different interpreters.</p>
 *
 * <p>This should be useful for simpler GUIs where you get the option to toggle an endian-ness flag.</p>
 */
public class EndianOptionInterpreterInfo extends AbstractInterpreterInfo {
    private final Interpreter<?> bigEndianInterpreter;
    private final Interpreter<?> littleEndianInterpreter;

    public EndianOptionInterpreterInfo(Name name,
                                       Interpreter<?> bigEndianInterpreter,
                                       Interpreter<?> littleEndianInterpreter)
    {
        super(name);
        this.bigEndianInterpreter = bigEndianInterpreter;
        this.littleEndianInterpreter = littleEndianInterpreter;
    }

    @Override
    public List<Option> getOptions() {
        return Arrays.asList(new Option("byteOrder", ByteOrder.class, true));
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        ByteOrder byteOrder = (ByteOrder) options.get("byteOrder");
        return byteOrder == ByteOrder.BIG_ENDIAN ? bigEndianInterpreter : littleEndianInterpreter;
    }
}
