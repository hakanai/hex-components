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
    public void testAddingGroupAroundTwoAnnotations() throws Exception {
        createCollection(100);

        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf 1");
        expectAddedEvent(20, 10);
        addLeaf(20, 10, "leaf 2");

        expectRemovedEvent(10, 10, 20, 10);
        expectAddedEvent(10, 20);
        addGroup(10, 20, "group");

        assertStructure(new Object[] { null, new Object[] { "group", "leaf 1", "leaf 2" }});
    }

    @Test
    public void testAddingOverlappingAnnotation() throws Exception {
        createCollection(100);

        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf 1");

        try {
            addLeaf(10, 10, "overlapping");
        } catch (OverlappingAnnotationException e) {
            // Expected.
        }
    }

    @Test
    public void testAddingOverlappingAnnotationViaGroup() throws Exception {
        createCollection(100);

        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf 1");

        try {
            GroupAnnotation group = new SimpleGroupAnnotation(0, 30);
            group.add(new SimpleAnnotation(10, 10, new NullInterpreter()));
            collection.add(group);
        } catch (OverlappingAnnotationException e) {
            // Expected.
        }
    }

    @Test
    public void testNearlyOverlappingAnnotationViaGroup() throws Exception {
        createCollection(100);

        expectAddedEvent(10, 10);
        addLeaf(10, 10, "leaf 1");

        GroupAnnotation group = new SimpleGroupAnnotation(0, 30);
        group.add(new SimpleAnnotation(0, 10, new NullInterpreter()));
        group.add(new SimpleAnnotation(20, 10, new NullInterpreter()));

        // Adds these to test for collision.
        //TODO: This is an edge case, but these nodes get added only to get removed again.
        expectAddedEvent(0, 10);
        expectAddedEvent(20, 10);
        // Then all is fine so it performs the usual addition.
        expectRemovedEvent(0, 10, 10, 10, 20, 10);
        expectAddedEvent(0, 30);
        collection.add(group);
    }

    @Test
    public void testRemovingGroupWithChild() throws Exception {
        createCollection(100);

        expectAddedEvent(20, 20);
        addLeaf(20, 20, "leaf");
        expectRemovedEvent(20, 20);
        expectAddedEvent(20, 40);
        GroupAnnotation annotation = addGroup(20, 40, "group");

        expectRemovedEvent(20, 40);
        expectAddedEvent(20, 20);
        collection.remove(annotation);

        assertStructure(new Object[]{null, "leaf"});
    }

    @Test
    public void testRemovingGroupWithTwoChildren() throws Exception {
        createCollection(100);

        expectAddedEvent(20, 20);
        addLeaf(20, 20, "leaf 1");
        expectAddedEvent(40, 20);
        addLeaf(40, 20, "leaf 2");
        expectRemovedEvent(20, 20, 40, 20);
        expectAddedEvent(20, 40);
        GroupAnnotation annotation = addGroup(20, 40, "group");

        expectRemovedEvent(20, 40);
        expectAddedEvent(20, 20, 40, 20);
        collection.remove(annotation);

        assertStructure(new Object[]{null, "leaf 1", "leaf 2"});
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

    private void expectAddedEvent(final long... info) {
        mockery.checking(new Expectations() {{
            oneOf(listener).annotationsAdded(with(annotationCollectionEvent(info)));
        }});
    }

    private void expectRemovedEvent(final long... info) {
        mockery.checking(new Expectations() {{
            oneOf(listener).annotationsRemoved(with(annotationCollectionEvent(info)));
        }});
    }

    private Matcher<AnnotationCollectionEvent> annotationCollectionEvent(final long... info) {
        return new TypeSafeMatcher<AnnotationCollectionEvent>() {
            @Override
            public boolean matchesSafely(AnnotationCollectionEvent event) {
                int expectedChildCount = info.length / 2;
                List<Integer> childIndices = event.getChildIndices();
                List<Annotation> children = event.getChildren();
                if (childIndices.size() != expectedChildCount || children.size() != expectedChildCount) {
                    return false;
                }

                for (int i = 0; i < info.length; i += 2) {
                    Annotation annotation = children.get(i / 2);
                    long expectedPosition = info[i];
                    long expectedLength = info[i + 1];
                    if (annotation.getPosition() != expectedPosition ||
                        annotation.getLength() != expectedLength) {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("event with annotations: ");
                description.appendValue(info);
            }
        };
    }

    private GroupAnnotation addGroup(long position, long length, String note) throws Exception {
        GroupAnnotation group = new SimpleGroupAnnotation(position, length);
        group.set(CommonAttributes.NOTE, note);
        collection.add(group);
        return group;
    }

    private Annotation addLeaf(long position, long length, String note) throws Exception {
        Annotation leaf = new SimpleAnnotation(position, length, new NullInterpreter());
        leaf.set(CommonAttributes.NOTE, note);
        collection.add(leaf);
        return leaf;
    }

    private void assertStructure(Object[] expected) {
        assertStructure(collection.getRootGroup(), expected);
    }

    private void assertStructure(GroupAnnotation group, Object[] expected) {
        assertEquals("Wrong node (note didn't match)", expected[0], group.get(CommonAttributes.NOTE));
        List<? extends Annotation> children = group.getAnnotations();
        assertEquals("Wrong number of children inside " + group, expected.length - 1, children.size());
        for (int i = 1; i < expected.length; i++) {
            Annotation child = children.get(i - 1);
            if (child instanceof GroupAnnotation) {
                assertStructure((GroupAnnotation) child, (Object[]) expected[i]);
            } else {
                assertEquals("Wrong node (note didn't match)", expected[i], child.get(CommonAttributes.NOTE));
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
