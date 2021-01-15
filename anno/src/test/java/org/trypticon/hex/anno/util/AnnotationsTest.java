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

import org.junit.jupiter.api.Test;
import org.trypticon.hex.anno.Annotation;
import org.trypticon.hex.anno.SimpleAnnotation;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.trypticon.hex.anno.util.Annotations.*;

/**
 * Tests for {@link Annotations}.
 *
 * @author trejkaz
 */
public class AnnotationsTest {

    @Test
    public void testContains_OverlapOfOnePosition() {
        assertThat(contains(a(0, 10), a(9, 10)), is(false));
    }

    @Test
    public void testContains_TouchingRegions() {
        assertThat(contains(a(0, 10), a(10, 10)), is(false));
    }

    @Test
    public void testContains_FirstContainedInSecond() {
        assertThat(contains(a(0, 5), a(0, 10)), is(false));
    }

    @Test
    public void testContains_SecondContainedInFirst() {
        assertThat(contains(a(0, 10), a(0, 5)), is(true));
    }

    @Test
    public void testContains_SameRegion() {
        assertThat(contains(a(0, 10), a(0, 10)), is(true));
    }

    @Test
    public void testOverlap_OverlapOfOnePosition() {
        assertThat(overlap(a(0, 10), a(9, 10)), is(true));
    }

    @Test
    public void testOverlap_TouchingRegions() {
        assertThat(overlap(a(0, 10), a(10, 10)), is(false));
    }

    @Test
    public void testOverlap_FirstContainedInSecond() {
        assertThat(overlap(a(0, 5), a(0, 10)), is(true));
    }

    @Test
    public void testOverlap_SecondContainedInFirst() {
        assertThat(overlap(a(0, 10), a(0, 5)), is(true));
    }

    @Test
    public void testOverlap_SameRegion() {
        assertThat(overlap(a(0, 10), a(0, 10)), is(true));
    }

    private static Annotation a(int start, int length) {
        return new SimpleAnnotation(start, length, new NullInterpreter());
    }
}
