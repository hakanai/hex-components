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
    private final List<Annotation> parentAnnotationPath;
    private final Annotation annotation;

    public AnnotationCollectionEvent(AnnotationCollection collection) {
        this(collection, null, null);
    }

    public AnnotationCollectionEvent(AnnotationCollection collection,
                                     List<? extends Annotation> parentAnnotationPath, Annotation annotation) {
        super(collection);
        this.parentAnnotationPath = new ArrayList<>(parentAnnotationPath);
        this.annotation = annotation;
    }

    @Override
    public AnnotationCollection getSource() {
        return (AnnotationCollection) super.getSource();
    }

    public List<Annotation> getParentAnnotationPath() {
        return Collections.unmodifiableList(parentAnnotationPath);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return super.toString() + " " + annotation;
    }
}
