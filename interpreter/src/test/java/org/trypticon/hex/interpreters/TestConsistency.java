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

import org.junit.Test;
import org.trypticon.hex.util.Format;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * Tests for overall consistency of the implementation.
 */
public class TestConsistency {
    @Test
    public void testInternationalisation() {
        check(new MasterInterpreterStorage().getGroupedInterpreterInfos());
        check(new MasterInterpreterStorage().getInterpreterInfos());
    }

    private void check(List<InterpreterInfo> infos) {
        Set<String> shortNames = new LinkedHashSet<>();
        Set<String> longNames = new LinkedHashSet<>();
        for (InterpreterInfo info : infos) {
            String shortName = info.toLocalisedString(Format.SHORT, Locale.ROOT);
            if (!shortNames.add(shortName)) {
                fail("Duplicate short name: " + shortName);
            }
            String longName = info.toLocalisedString(Format.LONG, Locale.ROOT);
            if (!longNames.add(longName)) {
                fail("Duplicate long name: " + longName);
            }
        }
    }
}
