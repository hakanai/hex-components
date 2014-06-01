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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

/**
 * Event for changes in an annotation listener.
 *
 * @author trejkaz
 */
public class AnnotationCollectionEvent extends EventObject {
    private final List<GroupAnnotation> parentPath;
    private final List<Integer> childIndices;
    private final List<Annotation> children;

    public AnnotationCollectionEvent(AnnotationCollection collection,
                                     List<? extends GroupAnnotation> parentPath,
                                     List<Integer> childIndices,
                                     List<Annotation> children) {
        super(collection);
        this.parentPath = new ArrayList<>(parentPath);
        this.childIndices = new ArrayList<>(childIndices);
        this.children = new ArrayList<>(children);
    }

    @Override
    public AnnotationCollection getSource() {
        return (AnnotationCollection) super.getSource();
    }

    public List<GroupAnnotation> getParentPath() {
        return Collections.unmodifiableList(parentPath);
    }

    public List<Integer> getChildIndices() {
        return Collections.unmodifiableList(childIndices);
    }

    public List<Annotation> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
        return super.toString() + " " + children;
    }
}
