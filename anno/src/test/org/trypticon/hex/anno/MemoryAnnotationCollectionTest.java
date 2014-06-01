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

package org.trypticon.hex.anno;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link MemoryAnnotationCollection}.
 *
 * @author trejkaz
 */
public class MemoryAnnotationCollectionTest {
    Mockery mockery = new Mockery();
    AnnotationCollectionListener listener;
    MemoryAnnotationCollection collection;

    @Test
    public void testAddingAnnotationInsideGroupAtStartPosition() throws Exception {
        createCollection(100);

        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");
        expectAddedEvent(0, 10);
        addLeaf(0, 10, "leaf");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testAddingGroupAroundAnnotationAtStartPosition() throws Exception {
        createCollection(100);

        expectAddedEvent(0, 10);
        addLeaf(0, 10, "leaf");
        expectRemovedEvent(0, 10);
        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testAddingAnnotationInsideGroupAtMiddlePosition() throws Exception {
        createCollection(100);

        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");
        expectAddedEvent(5, 10);
        addLeaf(5, 10, "leaf");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testAddingGroupAroundAnnotationAtMiddlePosition() throws Exception {
        createCollection(100);

        expectAddedEvent(5, 10);
        addLeaf(5, 10, "leaf");
        expectRemovedEvent(5, 10);
        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testAddingAnnotationInsideGroupAtEndPosition() throws Exception {
        createCollection(100);

        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");
        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testAddingGroupAroundAnnotationAtEndPosition() throws Exception {
        createCollection(100);

        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf");
        expectRemovedEvent(10, 10);
        expectAddedEvent(0, 20);
        addGroup(0, 20, "group");

        assertSingleLeafInsideSingleGroup();
    }

    @Test
    public void testRemovingGroupWithChildren() throws Exception {
        createCollection(100);

        expectAddedEvent(20, 20);
        addLeaf(20, 20, "leaf");
        expectRemovedEvent(20, 20);
        expectAddedEvent(20, 40);
        Annotation annotation = addGroup(20, 40, "group");

        expectRemovedEvent(20, 40);
        expectAddedEvent(20, 20);
        collection.remove(annotation);

        assertStructure(new Object[]{null, "leaf"});
    }

    @Test
    public void testRemovingLeaf() throws Exception {
        createCollection(100);
        expectAddedEvent(20, 20);
        Annotation annotation = addLeaf(20, 20, "leaf");
        expectRemovedEvent(20, 20);
        expectAddedEvent(20, 40);
        addGroup(20, 40, "group");

        expectRemovedEvent(20, 20);
        collection.remove(annotation);

        assertStructure(new Object[]{null, new Object[] { "group" }});
    }

    private void assertSingleLeafInsideSingleGroup() {
        assertStructure(new Object[] { null,
                            new Object[] { "group",
                                "leaf"
                            }
                        });
    }

    private void createCollection(int binarySize) {
        collection = new MemoryAnnotationCollection(binarySize);
        collection.addAnnotationCollectionListener(listener);
    }

    private void expectAddedEvent(final long position, final long length) {
        mockery.checking(new Expectations() {{
            oneOf(listener).annotationAdded(with(annotationCollectionEvent(position, length)));
        }});
    }

    private void expectRemovedEvent(final long position, final long length) {
        mockery.checking(new Expectations() {{
            oneOf(listener).annotationRemoved(with(annotationCollectionEvent(position, length)));
        }});
    }

    private Matcher<AnnotationCollectionEvent> annotationCollectionEvent(final long position, final long length) {
        return new TypeSafeMatcher<AnnotationCollectionEvent>() {
            @Override
            public boolean matchesSafely(AnnotationCollectionEvent event) {
                Annotation annotation = event.getAnnotation();
                return annotation.getPosition() == position &&
                       annotation.getLength() == length;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("event with annotation at position ");
                description.appendValue(position);
                description.appendText(" with length ");
                description.appendValue(length);
            }
        };
    }

    private Annotation addGroup(long position, long length, String note) throws Exception {
        GroupAnnotation group = new SimpleMutableGroupAnnotation(position, length, note);
        collection.add(group);
        return group;
    }

    private Annotation addLeaf(long position, long length, String note) throws Exception {
        Annotation leaf = new SimpleMutableAnnotation(position, length, new NullInterpreter(), note);
        collection.add(leaf);
        return leaf;
    }

    private void assertStructure(Object[] expected) {
        assertStructure(collection.getRootGroup(), expected);
    }

    private void assertStructure(GroupAnnotation group, Object[] expected) {
        assertEquals("Wrong node (note didn't match)", expected[0], group.getNote());
        List<Annotation> children = group.getAnnotations();
        assertEquals("Wrong number of children inside " + group, expected.length - 1, children.size());
        for (int i = 1; i < expected.length; i++) {
            Annotation child = children.get(i - 1);
            if (child instanceof GroupAnnotation) {
                assertStructure((GroupAnnotation) child, (Object[]) expected[i]);
            } else {
                assertEquals("Wrong node (note didn't match)", expected[i], child.getNote());
            }
        }
    }

    @Before
    public void setUp() {
        listener = mockery.mock(AnnotationCollectionListener.class);
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
    }
}
