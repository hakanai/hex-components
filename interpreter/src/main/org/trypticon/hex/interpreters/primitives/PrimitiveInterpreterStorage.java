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

package org.trypticon.hex.interpreters.primitives;

import org.trypticon.hex.interpreters.AbstractInterpreterStorage;
import org.trypticon.hex.interpreters.InterpreterInfo;
import org.trypticon.hex.interpreters.meta.AutoLengthInterpreterInfo;
import org.trypticon.hex.interpreters.meta.EndianOptionInterpreterInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SByteInterpreter;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UByteInterpreter;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterLE;
import org.trypticon.hex.util.LocalisedName;

import java.util.Arrays;
import java.util.List;

/**
 * Storage support for primitive interpreters.
 *
 * @author trejkaz
 */
public class PrimitiveInterpreterStorage extends AbstractInterpreterStorage {

    public PrimitiveInterpreterStorage() {
        register("sint8", SByteInterpreter.class);
        register("sint16_be", SShortInterpreterBE.class);
        register("sint16_le", SShortInterpreterLE.class);
        register("sint32_be", SIntInterpreterBE.class);
        register("sint32_le", SIntInterpreterLE.class);
        register("sint64_be", SLongInterpreterBE.class);
        register("sint64_le", SLongInterpreterLE.class);
        register("uint8", UByteInterpreter.class);
        register("uint16_be", UShortInterpreterBE.class);
        register("uint16_le", UShortInterpreterLE.class);
        register("uint32_be", UIntInterpreterBE.class);
        register("uint32_le", UIntInterpreterLE.class);
        register("uint64_be", ULongInterpreterBE.class);
        register("uint64_le", ULongInterpreterLE.class);
        register("float16_be", Float16InterpreterBE.class);
        register("float16_le", Float16InterpreterLE.class);
        register("float32_be", Float32InterpreterBE.class);
        register("float32_le", Float32InterpreterLE.class);
        register("float64_be", Float64InterpreterBE.class);
        register("float64_le", Float64InterpreterLE.class);
        register("float128_be", Float128InterpreterBE.class);
        register("float128_le", Float128InterpreterLE.class);
    }

    @Override
    public List<InterpreterInfo> getGroupedInterpreterInfos() {
        return Arrays.<InterpreterInfo>asList(
                new AutoLengthInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedSigned"),
                        PrimitiveInterpreters.SINT8_INFO,
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.SInt16"),
                                PrimitiveInterpreters.SINT16_BE,
                                PrimitiveInterpreters.SINT16_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.SInt32"),
                                PrimitiveInterpreters.SINT32_BE,
                                PrimitiveInterpreters.SINT32_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.SInt64"),
                                PrimitiveInterpreters.SINT64_BE,
                                PrimitiveInterpreters.SINT64_LE)),
                new AutoLengthInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedUnsigned"),
                        PrimitiveInterpreters.UINT8_INFO,
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.UInt16"),
                                PrimitiveInterpreters.UINT16_BE,
                                PrimitiveInterpreters.UINT16_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.UInt32"),
                                PrimitiveInterpreters.UINT32_BE,
                                PrimitiveInterpreters.UINT32_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.UInt64"),
                                PrimitiveInterpreters.UINT64_BE,
                                PrimitiveInterpreters.UINT64_LE)),
                new AutoLengthInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedFloat"),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Float16"),
                                PrimitiveInterpreters.FLOAT16_BE,
                                PrimitiveInterpreters.FLOAT16_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Float32"),
                                PrimitiveInterpreters.FLOAT32_BE,
                                PrimitiveInterpreters.FLOAT32_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Float64"),
                                PrimitiveInterpreters.FLOAT64_BE,
                                PrimitiveInterpreters.FLOAT64_LE),
                        new EndianOptionInterpreterInfo<>(
                                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.Float128"),
                                PrimitiveInterpreters.FLOAT128_BE,
                                PrimitiveInterpreters.FLOAT128_LE)));
    }

    @Override
    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.asList(
                PrimitiveInterpreters.SINT8_INFO,
                PrimitiveInterpreters.SINT16_BE_INFO,
                PrimitiveInterpreters.SINT16_LE_INFO,
                PrimitiveInterpreters.SINT32_BE_INFO,
                PrimitiveInterpreters.SINT32_LE_INFO,
                PrimitiveInterpreters.SINT64_BE_INFO,
                PrimitiveInterpreters.SINT64_LE_INFO,
                PrimitiveInterpreters.UINT8_INFO,
                PrimitiveInterpreters.UINT16_BE_INFO,
                PrimitiveInterpreters.UINT16_LE_INFO,
                PrimitiveInterpreters.UINT32_BE_INFO,
                PrimitiveInterpreters.UINT32_LE_INFO,
                PrimitiveInterpreters.UINT64_BE_INFO,
                PrimitiveInterpreters.UINT64_LE_INFO,
                PrimitiveInterpreters.FLOAT16_BE_INFO,
                PrimitiveInterpreters.FLOAT16_LE_INFO,
                PrimitiveInterpreters.FLOAT32_BE_INFO,
                PrimitiveInterpreters.FLOAT32_LE_INFO,
                PrimitiveInterpreters.FLOAT64_BE_INFO,
                PrimitiveInterpreters.FLOAT64_LE_INFO,
                PrimitiveInterpreters.FLOAT128_BE_INFO,
                PrimitiveInterpreters.FLOAT128_LE_INFO);
    }
}
