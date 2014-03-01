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

import java.util.Arrays;
import java.util.List;

/**
 * Storage support for primitive interpreters.
 *
 * @author trejkaz
 */
public class PrimitiveInterpreterStorage extends AbstractInterpreterStorage {

    public PrimitiveInterpreterStorage() {
        register("uint8", UByteInterpreter.class);
        register("uint16_be", UShortInterpreterBE.class);
        register("uint16_le", UShortInterpreterLE.class);
        register("uint32_be", UIntInterpreterBE.class);
        register("uint32_le", UIntInterpreterLE.class);
        register("uint64_be", ULongInterpreterBE.class);
        register("uint64_le", ULongInterpreterLE.class);
        register("float32_be", FloatInterpreterBE.class);
        register("float32_le", FloatInterpreterLE.class);
        register("float64_be", DoubleInterpreterBE.class);
        register("float64_le", DoubleInterpreterLE.class);
    }

    public List<InterpreterInfo> getInterpreterInfos() {
        // TODO: Interpreter info should be structured to allow categorising them as well, for menus.

        return Arrays.asList(new UByteInterpreterInfo(),
                             new UShortInterpreterBEInfo(),
                             new UShortInterpreterLEInfo(),
                             new UIntInterpreterBEInfo(),
                             new UIntInterpreterLEInfo(),
                             new ULongInterpreterBEInfo(),
                             new ULongInterpreterLEInfo(),
                             new FloatInterpreterBEInfo(),
                             new FloatInterpreterLEInfo(),
                             new DoubleInterpreterBEInfo(),
                             new DoubleInterpreterLEInfo());
    }
}
