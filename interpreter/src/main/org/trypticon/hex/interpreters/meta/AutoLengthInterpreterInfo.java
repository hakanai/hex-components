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
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.InterpreterInfo;
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
    private final InterpreterInfo[] infos;
    private final List<Option> options;

    public AutoLengthInterpreterInfo(Localisable name, InterpreterInfo... infos) {
        super(name);

        this.infos = Arrays.copyOf(infos, infos.length);

        LinkedHashSet<Option> options = new LinkedHashSet<>(4);
        for (InterpreterInfo info : infos) {
            options.addAll(info.getOptions());
        }
        this.options = new ArrayList<>(options);
    }

    @Override
    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    @Override
    public Interpreter create(Map<String, Object> options) {
        List<FixedLengthInterpreter<?>> interpreters = new ArrayList<>(infos.length);
        for (InterpreterInfo info : infos) {
            Interpreter interpreter = info.create(options);
            if (interpreter instanceof FixedLengthInterpreter) {
                interpreters.add((FixedLengthInterpreter) interpreter);
            } else {
                throw new IllegalStateException("Interpreter was not a FixedLengthInterpreter: " + interpreter);
            }
        }
        return new AutoLengthInterpreter(interpreters);
    }
}
