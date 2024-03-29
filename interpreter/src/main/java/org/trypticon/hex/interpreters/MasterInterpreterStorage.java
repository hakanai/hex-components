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

package org.trypticon.hex.interpreters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.trypticon.hex.interpreters.dates.DateInterpreterStorage;
import org.trypticon.hex.interpreters.nulls.NullInterpreterStorage;
import org.trypticon.hex.interpreters.primitives.PrimitiveInterpreterStorage;
import org.trypticon.hex.interpreters.strings.StringInterpreterStorage;

/**
 * Storage routines for annotations.  Basically these just convert to and
 * from maps, which can then be converted into whatever format the caller
 * wants.
 *
 * @author trejkaz
 */
public class MasterInterpreterStorage implements InterpreterStorage {
    private final List<InterpreterStorage> providers = new ArrayList<>(10);

    public MasterInterpreterStorage() {
        // TODO: Look up using Service Provider API.
        providers.add(new NullInterpreterStorage());
        providers.add(new PrimitiveInterpreterStorage());
        providers.add(new DateInterpreterStorage());
        providers.add(new StringInterpreterStorage());
    }

    @Override
    public List<InterpreterInfo> getGroupedInterpreterInfos() {
        List<InterpreterInfo> infos = new ArrayList<>();
        for (InterpreterStorage provider : providers) {
            infos.addAll(provider.getGroupedInterpreterInfos());
        }
        return Collections.unmodifiableList(infos);
    }

    @Override
    public List<InterpreterInfo> getInterpreterInfos() {
        List<InterpreterInfo> infos = new ArrayList<>();
        for (InterpreterStorage provider : providers) {
            infos.addAll(provider.getInterpreterInfos());
        }
        return Collections.unmodifiableList(infos);
    }

    @Nullable
    @Override
    public Map<String, Object> toMap(Interpreter<?> interpreter) {
        for (InterpreterStorage provider : providers) {
            Map<String, Object> map = provider.toMap(interpreter);
            if (map != null) {
                return map;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Interpreter<?> fromMap(Map<String, Object> map) {
        for (InterpreterStorage provider : providers) {
            Interpreter<?> interpreter = provider.fromMap(map);
            if (interpreter != null) {
                return interpreter;
            }
        }
        return null;
    }
}
