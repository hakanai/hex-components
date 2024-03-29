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

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    @Nonnull
    GroupAnnotation getRootGroup();

    /**
     * Gets a list of the top-level annotations.
     *
     * @return the list of the top-level annotations.
     */
    @Nonnull
    List<? extends Annotation> getTopLevel();

    /**
     * Gets the annotation path from the root to the leaf annotation node.
     *
     * @param position the position to look up.
     * @return the path to the deepest annotation at that position, including the group annotations
     *         which contain it.  Returns {@code null} if outside all annotations.
     */
    @Nullable
    List<? extends Annotation> getAnnotationPathAt(long position);

    /**
     * Gets the path to a given annotation.
     *
     * @param annotation the annotation to look up.
     * @return the path to that annotation.
     * @throws IllegalArgumentException if the annotation is not in the collection.
     */
    @Nonnull
    List<? extends Annotation> getAnnotationPathFor(Annotation annotation);

    /**
     * Adds an annotation.
     *
     * @param annotation the annotation to add.
     * @throws OverlappingAnnotationException if the annotation overlaps an existing one.
     */
    void add(@Nonnull Annotation annotation) throws OverlappingAnnotationException;

    /**
     * Removes an annotation.
     *
     * @param annotation the annotation to remove.
     */
    void remove(@Nonnull Annotation annotation);

    /**
     * Removes an annotation along with the entire tree underneath it.
     *
     * @param annotation the annotation to remove.
     */
    void removeWithDescendants(@Nonnull Annotation annotation);

    /**
     * Adds a listener for changes in the collection.
     *
     * @param listener the listener to add.
     */
    void addAnnotationCollectionListener(@Nonnull AnnotationCollectionListener listener);

    /**
     * Removes a listener for changes in the collection.
     *
     * @param listener the listener to remove.
     */
    void removeAnnotationCollectionListener(@Nonnull AnnotationCollectionListener listener);
}
