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

package org.trypticon.hex.anno.util;

import org.junit.Test;
import org.trypticon.hex.anno.Annotation;
import org.trypticon.hex.anno.SimpleAnnotation;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.trypticon.hex.anno.util.Annotations.*;

/**
 * Tests for {@link Annotations}.
 *
 * @author trejkaz
 */
public class AnnotationsTest {

    @Test
    public void testContains_OverlapOfOnePosition() {
        assertFalse(contains(a(0, 10), a(9, 10)));
    }

    @Test
    public void testContains_TouchingRegions() {
        assertFalse(contains(a(0, 10), a(10, 10)));
    }

    @Test
    public void testContains_FirstContainedInSecond() {
        assertFalse(contains(a(0, 5), a(0, 10)));
    }

    @Test
    public void testContains_SecondContainedInFirst() {
        assertTrue(contains(a(0, 10), a(0, 5)));
    }

    @Test
    public void testContains_SameRegion() {
        assertTrue(contains(a(0, 10), a(0, 10)));
    }

    @Test
    public void testOverlap_OverlapOfOnePosition() {
        assertTrue(overlap(a(0, 10), a(9, 10)));
    }

    @Test
    public void testOverlap_TouchingRegions() {
        assertFalse(overlap(a(0, 10), a(10, 10)));
    }

    @Test
    public void testOverlap_FirstContainedInSecond() {
        assertTrue(overlap(a(0, 5), a(0, 10)));
    }

    @Test
    public void testOverlap_SecondContainedInFirst() {
        assertTrue(overlap(a(0, 10), a(0, 5)));
    }

    @Test
    public void testOverlap_SameRegion() {
        assertTrue(overlap(a(0, 10), a(0, 10)));
    }

    private static Annotation a(int start, int length) {
        return new SimpleAnnotation(start, length, new NullInterpreter());
    }
}
