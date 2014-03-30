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

package org.trypticon.hex.interpreters.primitives;

import org.trypticon.hex.interpreters.FixedLengthInterpreter;
import org.trypticon.hex.interpreters.FixedLengthInterpreterInfo;
import org.trypticon.hex.interpreters.meta.SingleFixedLengthInterpreterInfo;
import org.trypticon.hex.interpreters.primitives.floating.Float128;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float128InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float16;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float16InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float32;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float32InterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float64;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.Float64InterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SByte;
import org.trypticon.hex.interpreters.primitives.signed.SByteInterpreter;
import org.trypticon.hex.interpreters.primitives.signed.SInt;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SLong;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SLongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.signed.SShort;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.signed.SShortInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UByte;
import org.trypticon.hex.interpreters.primitives.unsigned.UByteInterpreter;
import org.trypticon.hex.interpreters.primitives.unsigned.UInt;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UIntInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULong;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.ULongInterpreterLE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShort;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterBE;
import org.trypticon.hex.interpreters.primitives.unsigned.UShortInterpreterLE;
import org.trypticon.hex.util.LocalisedName;

/**
 * Convenience class holding a bunch of instances to primitive interpreters.
 *
 * @author trejkaz
 */
public class PrimitiveInterpreters {
    private PrimitiveInterpreters() {
    }

    public static final FixedLengthInterpreter<SByte> SINT8 = new SByteInterpreter();
    public static final FixedLengthInterpreter<SShort> SINT16_BE = new SShortInterpreterBE();
    public static final FixedLengthInterpreter<SShort> SINT16_LE = new SShortInterpreterLE();
    public static final FixedLengthInterpreter<SInt> SINT32_BE = new SIntInterpreterBE();
    public static final FixedLengthInterpreter<SInt> SINT32_LE = new SIntInterpreterLE();
    public static final FixedLengthInterpreter<SLong> SINT64_BE = new SLongInterpreterBE();
    public static final FixedLengthInterpreter<SLong> SINT64_LE = new SLongInterpreterLE();

    public static final FixedLengthInterpreter<UByte> UINT8 = new UByteInterpreter();
    public static final FixedLengthInterpreter<UShort> UINT16_BE = new UShortInterpreterBE();
    public static final FixedLengthInterpreter<UShort> UINT16_LE = new UShortInterpreterLE();
    public static final FixedLengthInterpreter<UInt> UINT32_BE = new UIntInterpreterBE();
    public static final FixedLengthInterpreter<UInt> UINT32_LE = new UIntInterpreterLE();
    public static final FixedLengthInterpreter<ULong> UINT64_BE = new ULongInterpreterBE();
    public static final FixedLengthInterpreter<ULong> UINT64_LE = new ULongInterpreterLE();

    public static final FixedLengthInterpreter<Float16> FLOAT16_BE = new Float16InterpreterBE();
    public static final FixedLengthInterpreter<Float16> FLOAT16_LE = new Float16InterpreterLE();
    public static final FixedLengthInterpreter<Float32> FLOAT32_BE = new Float32InterpreterBE();
    public static final FixedLengthInterpreter<Float32> FLOAT32_LE = new Float32InterpreterLE();
    public static final FixedLengthInterpreter<Float64> FLOAT64_BE = new Float64InterpreterBE();
    public static final FixedLengthInterpreter<Float64> FLOAT64_LE = new Float64InterpreterLE();
    public static final FixedLengthInterpreter<Float128> FLOAT128_BE = new Float128InterpreterBE();
    public static final FixedLengthInterpreter<Float128> FLOAT128_LE = new Float128InterpreterLE();

    public static final FixedLengthInterpreterInfo SINT8_INFO = info("SInt8", SINT8);
    public static final FixedLengthInterpreterInfo SINT16_BE_INFO = info("SInt16BE", SINT16_BE);
    public static final FixedLengthInterpreterInfo SINT16_LE_INFO = info("SInt16LE", SINT16_LE);
    public static final FixedLengthInterpreterInfo SINT32_BE_INFO = info("SInt32BE", SINT32_BE);
    public static final FixedLengthInterpreterInfo SINT32_LE_INFO = info("SInt32LE", SINT32_LE);
    public static final FixedLengthInterpreterInfo SINT64_BE_INFO = info("SInt64BE", SINT64_BE);
    public static final FixedLengthInterpreterInfo SINT64_LE_INFO = info("SInt64LE", SINT64_LE);

    public static final FixedLengthInterpreterInfo UINT8_INFO = info("UInt8", UINT8);
    public static final FixedLengthInterpreterInfo UINT16_BE_INFO = info("UInt16BE", UINT16_BE);
    public static final FixedLengthInterpreterInfo UINT16_LE_INFO = info("UInt16LE", UINT16_LE);
    public static final FixedLengthInterpreterInfo UINT32_BE_INFO = info("UInt32BE", UINT32_BE);
    public static final FixedLengthInterpreterInfo UINT32_LE_INFO = info("UInt32LE", UINT32_LE);
    public static final FixedLengthInterpreterInfo UINT64_BE_INFO = info("UInt64BE", UINT64_BE);
    public static final FixedLengthInterpreterInfo UINT64_LE_INFO = info("UInt64LE", UINT64_LE);

    public static final FixedLengthInterpreterInfo FLOAT16_BE_INFO = info("Float16BE", FLOAT16_BE);
    public static final FixedLengthInterpreterInfo FLOAT16_LE_INFO = info("Float16LE", FLOAT16_LE);
    public static final FixedLengthInterpreterInfo FLOAT32_BE_INFO = info("Float32BE", FLOAT32_BE);
    public static final FixedLengthInterpreterInfo FLOAT32_LE_INFO = info("Float32LE", FLOAT32_LE);
    public static final FixedLengthInterpreterInfo FLOAT64_BE_INFO = info("Float64BE", FLOAT64_BE);
    public static final FixedLengthInterpreterInfo FLOAT64_LE_INFO = info("Float64LE", FLOAT64_LE);
    public static final FixedLengthInterpreterInfo FLOAT128_BE_INFO = info("Float128BE", FLOAT128_BE);
    public static final FixedLengthInterpreterInfo FLOAT128_LE_INFO = info("Float128LE", FLOAT128_LE);

    private static FixedLengthInterpreterInfo info(String name, FixedLengthInterpreter<?> interpreter) {
        return new SingleFixedLengthInterpreterInfo(
                new LocalisedName("org/trypticon/hex/interpreters/Bundle", "Interpreters." + name), interpreter);
    }

}
