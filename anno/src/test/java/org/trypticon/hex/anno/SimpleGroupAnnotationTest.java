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

package org.trypticon.hex.anno;

import org.junit.jupiter.api.Test;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link SimpleGroupAnnotation}.
 *
 * @author trejkaz
 */
public class SimpleGroupAnnotationTest {

    GroupAnnotation group = new SimpleGroupAnnotation(0, 100);

    @Test
    public void testAdd_Consecutive() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        group.add(new SimpleAnnotation(40, 20, new NullInterpreter()));
    }

    @Test
    public void testRemove() throws Exception {
        Annotation annotation = new SimpleAnnotation(20, 20, new NullInterpreter());
        group.add(annotation);
        group.remove(annotation);
    }

    @Test
    public void testRemove_NotPresent() throws Exception {
        Annotation annotation = new SimpleAnnotation(20, 20, new NullInterpreter());
        assertThrows(IllegalArgumentException.class,
                     () -> group.remove(annotation));
    }

    @Test
    public void testFindAnnotationAt_Found_HitAtBottom() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findAnnotationAt(20), is(notNullValue()));
    }

    @Test
    public void testFindAnnotationAt_Found_HitAtTop() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findAnnotationAt(39), is(notNullValue()));
    }

    @Test
    public void testFindAnnotationAt_NotFound_MissedByOneBelow() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findAnnotationAt(19), is(nullValue()));
    }

    @Test
    public void testFindAnnotationAt_NotFound_MissedByOneAbove() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findAnnotationAt(40), is(nullValue()));
    }

    @Test
    public void testFindDeepestGroupAnnotationAt_NotFound() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findDeepestGroupAnnotationAt(19), is(nullValue()));
    }

    @Test
    public void testFindDeepestGroupAnnotationAt_NotFound_ButFoundNonGroup() throws Exception {
        group.add(new SimpleAnnotation(20, 20, new NullInterpreter()));
        assertThat(group.findDeepestGroupAnnotationAt(20), is(nullValue()));
    }

    @Test
    public void testFindDeepestGroupAnnotationAt_Found_OneDeep() throws Exception {
        GroupAnnotation level1 = new SimpleGroupAnnotation(20, 20);
        level1.set(CommonAttributes.NOTE, "level1");
        GroupAnnotation level2 = new SimpleGroupAnnotation(25, 10);
        level2.set(CommonAttributes.NOTE, "level2");
        group.add(level1);
        group.add(level2);
        assertThat(group.findDeepestGroupAnnotationAt(24), is(sameInstance(level1)));
    }

    @Test
    public void testFindDeepestGroupAnnotationAt_Found_TwoDeep() throws Exception {
        GroupAnnotation level1 = new SimpleGroupAnnotation(20, 20);
        level1.set(CommonAttributes.NOTE, "level1");
        GroupAnnotation level2 = new SimpleGroupAnnotation(25, 10);
        level2.set(CommonAttributes.NOTE, "level2");
        group.add(level1);
        group.add(level2);
        assertThat(group.findDeepestGroupAnnotationAt(25), is(sameInstance(level2)));
    }
}
