/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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

package org.trypticon.hex;

import org.trypticon.hex.anno.Annotation;

/**
 * Interface responsible for choosing the style to use when rendering a given annotation.
 *
 * @author trejkaz
 */
public interface AnnotationStyleScheme {

    /**
     * <p>Gets the style for a given annotation.</p>
     *
     * <p>It is recommended that the style objects you return <em>not</em> be created afresh for each invocation.
     *    Try to reuse {@code AnnotationStyle} objects as much as possible.</p>
     *
     * @param annotation the annotation.
     * @return the style to give the annotation.
     */
    AnnotationStyle getStyle(Annotation annotation);
}
