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

import org.trypticon.hex.anno.util.AnnotationPositionComparator;
import org.trypticon.hex.interpreters.nulls.NullInterpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a single annotation group.
 *
 * @author trejkaz
 */
public class SimpleMutableGroupAnnotation extends SimpleMutableAnnotation implements MutableGroupAnnotation {

    private final List<MutableAnnotation> annotations;

    public SimpleMutableGroupAnnotation(long position, long length, String note) {
        // TODO: Support interpreters for group annotations?  Or introduce a new level of hierarchy and
        //       have the Interpreter only on the leaf annotations?
        super(position, length, new NullInterpreter(), note);

        annotations = new ArrayList<>(4);
    }

    public SimpleMutableGroupAnnotation(long position, long length, String note,
                                        List<? extends MutableAnnotation> annotations) {
        // TODO: Support interpreters for group annotations?  Or introduce a new level of hierarchy and
        //       have the Interpreter only on the leaf annotations?
        super(position, length, new NullInterpreter(), note);

        this.annotations = new ArrayList<>(annotations);
    }

    @Override
    public List<? extends MutableAnnotation> getAnnotations() {
        return Collections.unmodifiableList(annotations);
    }

    @Override
    public Annotation findAnnotationAt(long position) {
        if (position < 0) {
            return null;
        }

        int pos = binaryPositionSearch(position);
        if (pos >= 0) {
            // Direct hit on the first position for an annotation.
            return annotations.get(pos);
        } else {
            // Find the nearest to the left.
            // -pos - 1 is the insertion point, so -pos - 2 would be the annotation before it.
            pos = -pos - 2;
            if (pos == -1) {
                // No annotations to the left, so impossible for one to cross the position we searched for.
                return null;
            }

            Annotation annotation = annotations.get(pos);

            // If it ends at the position passed in, or some point after it, then it's a match.
            long annotationEndPosition = annotation.getPosition() + annotation.getLength() - 1;
            if (annotationEndPosition >= position) {
                return annotation;
            } else {
                return null;
            }
        }
    }

    @Override
    public GroupAnnotation findDeepestGroupAnnotationAt(long position) {
        Annotation annotation = findAnnotationAt(position);
        if (!(annotation instanceof GroupAnnotation)) {
            return null;
        }

        GroupAnnotation groupAnnotation = (GroupAnnotation) annotation;
        GroupAnnotation deepest = groupAnnotation.findDeepestGroupAnnotationAt(position);
        if (deepest == null) {
            return groupAnnotation;
        } else {
            return deepest;
        }
    }

    @Override
    public int add(MutableAnnotation annotation) {
        int index = binaryPositionSearch(annotation.getPosition());
        if (index < 0) {
            index = -index - 1;
        }
        annotations.add(index, annotation);
        return index;
    }

    @Override
    public int remove(MutableAnnotation annotation) {
        int index = binaryPositionSearch(annotation.getPosition());
        if (index < 0) {
            throw new IllegalArgumentException("Annotation is not present so cannot be removed: " + annotation);
        }
        annotations.remove(annotation);
        return index;
    }

    @Override
    public void removeAllDescendants() {
        annotations.clear();
    }

    /**
     * Finds an annotation which crosses the position specified.
     *
     * @param position the position.
     * @return the index of an annotation which intersects the position.  If no annotations intersect the given
     *         position, then a negative value is returned where the insertion point can be determined by negating the
     *         result and subtracting one.
     */
    private int binaryPositionSearch(long position)
    {
        Annotation template = new SimpleMutableAnnotation(position, 1, new NullInterpreter(), null);
        return Collections.binarySearch(annotations, template, new AnnotationPositionComparator());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString()).append(" {\n");
        for (Annotation child : getAnnotations()) {
            builder.append(child).append('\n');
        }
        return builder.append("}\n").toString();
    }
}
