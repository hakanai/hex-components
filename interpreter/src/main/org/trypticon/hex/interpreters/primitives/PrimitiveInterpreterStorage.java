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
import org.trypticon.hex.interpreters.meta.AutoLengthInterpreter;
import org.trypticon.hex.interpreters.meta.EndianOptionInterpreterInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SByteInterpreter;
import org.trypticon.hex.interpreters.primitives.signed.SByteInterpreterInfo;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.UByteInterpreter;
import org.trypticon.hex.interpreters.primitives.unsigned.UByteInterpreterInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterLEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterBEInfo;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterLEInfo;
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
                new EndianOptionInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedSigned"),
                        new AutoLengthInterpreter(
                                new SByteInterpreter(),
                                new SShortInterpreterBE(),
                                new SIntInterpreterBE(),
                                new SLongInterpreterBE()),
                        new AutoLengthInterpreter(
                                new SByteInterpreter(),
                                new SShortInterpreterLE(),
                                new SIntInterpreterLE(),
                                new SLongInterpreterLE())),
                new EndianOptionInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedUnsigned"),
                        new AutoLengthInterpreter(
                                new UByteInterpreter(),
                                new UShortInterpreterBE(),
                                new UIntInterpreterBE(),
                                new ULongInterpreterBE()),
                        new AutoLengthInterpreter(
                                new UByteInterpreter(),
                                new UShortInterpreterLE(),
                                new UIntInterpreterLE(),
                                new ULongInterpreterLE())),
                new EndianOptionInterpreterInfo(
                        new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters.GroupedFloat"),
                        new AutoLengthInterpreter(
                                new Float16InterpreterBE(),
                                new Float32InterpreterBE(),
                                new Float64InterpreterBE(),
                                new Float128InterpreterBE()),
                        new AutoLengthInterpreter(
                                new Float16InterpreterLE(),
                                new Float32InterpreterLE(),
                                new Float64InterpreterLE(),
                                new Float128InterpreterLE())));
    }

    @Override
    public List<InterpreterInfo> getInterpreterInfos() {
        return Arrays.<InterpreterInfo>asList(
                new SByteInterpreterInfo(),
                new SShortInterpreterBEInfo(),
                new SShortInterpreterLEInfo(),
                new SIntInterpreterBEInfo(),
                new SIntInterpreterLEInfo(),
                new SLongInterpreterBEInfo(),
                new SLongInterpreterLEInfo(),
                new UByteInterpreterInfo(),
                new UShortInterpreterBEInfo(),
                new UShortInterpreterLEInfo(),
                new UIntInterpreterBEInfo(),
                new UIntInterpreterLEInfo(),
                new ULongInterpreterBEInfo(),
                new ULongInterpreterLEInfo(),
                new Float16InterpreterBEInfo(),
                new Float16InterpreterLEInfo(),
                new Float32InterpreterBEInfo(),
                new Float32InterpreterLEInfo(),
                new Float64InterpreterBEInfo(),
                new Float64InterpreterLEInfo(),
                new Float128InterpreterBEInfo(),
                new Float128InterpreterLEInfo());
    }
}
