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

import java.util.LinkedList;
import java.util.List;


/**
 * A collection of annotations kept in memory.
 *
 * @author trejkaz
 */
public class MemoryAnnotationCollection extends AbstractMutableAnnotationCollection {
    private final GroupAnnotation rootGroup;

    public MemoryAnnotationCollection(long length) {
        rootGroup = new SimpleMutableGroupAnnotation(0, length, null);
    }

    public MemoryAnnotationCollection(GroupAnnotation rootGroup) {
        this.rootGroup = rootGroup;
    }

    @Override
    public GroupAnnotation getRootGroup() {
        return rootGroup;
    }

    @Override
    public List<? extends Annotation> getTopLevel() {
        return rootGroup.getAnnotations();
    }

    @Override
    public List<? extends Annotation> getAnnotationPathAt(long position) {
        if (position < 0) {
            return null;
        }

        List<Annotation> path = null;
        Annotation annotation = rootGroup;

        while (annotation instanceof GroupAnnotation) {
            annotation = ((GroupAnnotation) annotation).findAnnotationAt(position);
            if (annotation == null) {
                break;
            }

            if (path == null) {
                path = new LinkedList<>();
            }
            path.add(annotation);
        }

        return path;
    }

    @Override
    public List<? extends Annotation> getAnnotationPathFor(Annotation annotation) {
        List<Annotation> path = new LinkedList<>();
        path.add(rootGroup);
        GroupAnnotation ancestor = rootGroup;
        while (true) {
            Annotation next = ancestor.findAnnotationAt(annotation.getPosition());
            if (next == null) {
                throw new IllegalArgumentException("Annotation is not in the collection: " + annotation);
            }

            path.add(next);
            if (next == annotation) {
                // Found it.
                return path;
            }

            if (next instanceof GroupAnnotation) {
                ancestor = (GroupAnnotation) next;
            } else {
                // Found a leaf, so the annotation can't be in the collection.
                throw new IllegalArgumentException("Annotation is not in the collection: " + annotation);
            }
        }
    }
}
