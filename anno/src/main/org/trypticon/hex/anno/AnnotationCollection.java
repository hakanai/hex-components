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

import java.util.List;

/**
 * A collection of annotations.
 *
 * @author trejkaz
 */
public interface AnnotationCollection {
    /**
     * Gets the root group annotation, covering the entire file.
     *
     * @return the root group annotation.
     */
    GroupAnnotation getRootGroup();

    /**
     * Gets a list of the top-level annotations.
     *
     * @return the list of the top-level annotations.
     */
    List<? extends Annotation> getTopLevel();

    /**
     * Gets the annotation path from the root to the leaf annotation node.
     *
     * @param position the position to look up.
     * @return the path to the deepest annotation at that position, including the group annotations
     *         which contain it.  Returns {@code null} if outside all annotations.
     */
    List<? extends Annotation> getAnnotationPathAt(long position);

    /**
     * Gets the path to a given annotation.
     *
     * @param annotation the annotation to look up.
     * @return the path to that annotation.
     * @throws IllegalArgumentException if tha annotation is not in the collection.
     */
    List<? extends Annotation> getAnnotationPathFor(Annotation annotation);

    /**
     * Adds a listener for changes in the collection.
     *
     * @param listener the listener to add.
     */
    void addAnnotationCollectionListener(AnnotationCollectionListener listener);

    /**
     * Removes a listener for changes in the collection.
     *
     * @param listener the listener to remove.
     */
    void removeAnnotationCollectionListener(AnnotationCollectionListener listener);
}
