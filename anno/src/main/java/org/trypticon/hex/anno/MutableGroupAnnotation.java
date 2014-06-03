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
 * Interface for mutable group annotations.
 *
 * @author trejkaz
 */
public interface MutableGroupAnnotation extends GroupAnnotation, MutableAnnotation {
    @Override
    List<? extends MutableAnnotation> getAnnotations();

    /**
     * Adds an annotation to the group annotation.
     *
     * @param annotation the annotation to add.
     * @return the index it was added at.
     */
    int add(MutableAnnotation annotation);

    /**
     * Removes an annotation from the group annotation.
     * If the removed annotation was a group, its original children are added back.
     *
     * @param annotation the annotation to remove.
     * @return the index it was removed from.
     * @throws IllegalArgumentException if the annotation isn't actually in the group.
     */
    int remove(MutableAnnotation annotation);

    /**
     * Removes all descendants.
     */
    void removeAllDescendants();
}