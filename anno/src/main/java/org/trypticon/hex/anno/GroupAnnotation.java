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
 * <p>A sub-region of the binary file.</p>
 *
 * <p>Covers a region of the binary file, and can also be nested.</p>
 *
 * @author trejkaz
 */
public interface GroupAnnotation extends Annotation {

    /**
     * Gets the list of contained annotations.
     *
     * @return the list of contained annotations.
     */
    List<? extends Annotation> getAnnotations();

    /**
     * Finds an annotation at the given position.
     *
     * @param position the position (relative to the entire file.)
     * @return the annotation found.  Returns {@code null} if no annotation is found.
     */
    Annotation findAnnotationAt(long position);

    /**
     * Finds the deepest group annotation at the given position.
     *
     * @param position the position (relative to the entire file.)
     * @return the group annotation found.  Returns {@code null} if this group annotation is the deepest.
     */
    GroupAnnotation findDeepestGroupAnnotationAt(long position);

    /**
     * Adds an annotation to the group annotation.
     *
     * @param annotation the annotation to add.
     * @return the index it was added at.
     */
    int add(Annotation annotation);

    /**
     * Removes an annotation from the group annotation.
     * If the removed annotation was a group, its original children are added back.
     *
     * @param annotation the annotation to remove.
     * @return the index it was removed from.
     * @throws IllegalArgumentException if the annotation isn't actually in the group.
     */
    int remove(Annotation annotation);

    /**
     * Removes all descendants.
     */
    void removeAllDescendants();
}
