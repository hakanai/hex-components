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

import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.primitives.floating.Double;
import org.trypticon.hex.interpreters.primitives.floating.DoubleInterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.DoubleInterpreterLE;
import org.trypticon.hex.interpreters.primitives.floating.Float;
import org.trypticon.hex.interpreters.primitives.floating.FloatInterpreterBE;
import org.trypticon.hex.interpreters.primitives.floating.FloatInterpreterLE;
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

/**
 * Convenience class holding a bunch of instances to primitive interpreters.
 *
 * @author trejkaz
 */
public class PrimitiveInterpreters {
    public static final Interpreter<SByte> SINT8 = new SByteInterpreter();
    public static final Interpreter<SShort> SINT16_BE = new SShortInterpreterBE();
    public static final Interpreter<SShort> SINT16_LE = new SShortInterpreterLE();
    public static final Interpreter<SInt> SINT32_BE = new SIntInterpreterBE();
    public static final Interpreter<SInt> SINT32_LE = new SIntInterpreterLE();
    public static final Interpreter<SLong> SINT64_BE = new SLongInterpreterBE();
    public static final Interpreter<SLong> SINT64_LE = new SLongInterpreterLE();

    public static final Interpreter<UByte> UINT8 = new UByteInterpreter();
    public static final Interpreter<UShort> UINT16_BE = new UShortInterpreterBE();
    public static final Interpreter<UShort> UINT16_LE = new UShortInterpreterLE();
    public static final Interpreter<UInt> UINT32_BE = new UIntInterpreterBE();
    public static final Interpreter<UInt> UINT32_LE = new UIntInterpreterLE();
    public static final Interpreter<ULong> UINT64_BE = new ULongInterpreterBE();
    public static final Interpreter<ULong> UINT64_LE = new ULongInterpreterLE();

    public static final Interpreter<Float> FLOAT32_BE = new FloatInterpreterBE();
    public static final Interpreter<Float> FLOAT32_LE = new FloatInterpreterLE();
    public static final Interpreter<Double> FLOAT64_BE = new DoubleInterpreterBE();
    public static final Interpreter<Double> FLOAT64_LE = new DoubleInterpreterLE();
}
