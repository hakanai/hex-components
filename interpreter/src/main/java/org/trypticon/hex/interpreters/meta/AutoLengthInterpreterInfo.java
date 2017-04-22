/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.options.LengthOption;
import org.trypticon.hex.util.Localisable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Interpreter info which provides multiple kinds of interpreter which differ only by length.
 */
public class AutoLengthInterpreterInfo extends AbstractInterpreterInfo {
    private final FixedLengthInterpreterInfo[] infos;
    private final List<Option<?>> options;

    public AutoLengthInterpreterInfo(Localisable name, FixedLengthInterpreterInfo... infos) {
        super(name);

        this.infos = Arrays.copyOf(infos, infos.length);

        LinkedHashSet<Option<?>> options = new LinkedHashSet<>(4);
        List<LengthOption> lengths = new ArrayList<>(infos.length + 1);
        lengths.add(LengthOption.AUTO);
        for (FixedLengthInterpreterInfo info : infos) {
            options.addAll(info.getOptions());
            lengths.add(LengthOption.fromBytes(info.getValueLength()));
        }
        options.add(new Option<>("length", LengthOption.class, true, lengths));
        this.options = new ArrayList<>(options);
    }

    @Override
    public List<Option<?>> getOptions() {
        return Collections.unmodifiableList(options);
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        LengthOption length = (LengthOption) options.get("length");
        if (length != null && length != LengthOption.AUTO) { //TODO: Surely AUTO == null
            for (FixedLengthInterpreterInfo info : infos) {
                if (LengthOption.fromBytes(info.getValueLength()) == length) {
                    return info.create(options);
                }
            }
            throw new IllegalArgumentException("Invalid length: " + length);
        } else {
            List<FixedLengthInterpreter<?>> interpreters = new ArrayList<>(infos.length);
            for (FixedLengthInterpreterInfo info : infos) {
                FixedLengthInterpreter interpreter = info.create(options);
                interpreters.add(interpreter);
            }
            return new AutoLengthInterpreter(interpreters);
        }
    }
}
