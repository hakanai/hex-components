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

import javax.annotation.Nonnull;

/**
 * Thrown when trying to add an {@link Annotation} to an {@link AnnotationCollection}, but
 * it would cross over some other annotation.
 */
public class OverlappingAnnotationException extends Exception {
    private static final long serialVersionUID = 1L;

    @Nonnull
    private final Annotation existing;

    @Nonnull
    private final Annotation attempted;

    public OverlappingAnnotationException(@Nonnull Annotation existing, @Nonnull Annotation attempted) {
        super("Overlapping annotations.\n  attempted to add: " + attempted +
              "\n  would have overlapped: " + existing);
        this.existing = existing;
        this.attempted = attempted;
    }

    @Nonnull
    public Annotation getExisting() {
        return existing;
    }

    @Nonnull
    public Annotation getAttempted() {
        return attempted;
    }

    // TODO: Reconstruct message after deserialisation
}
