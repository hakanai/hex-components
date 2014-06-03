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

package org.trypticon.hex;

import org.trypticon.hex.anno.Annotation;
import org.trypticon.hex.anno.GroupAnnotation;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Default style scheme which uses one colour for all group annotations and a different colour for
 * all non-group annotations. All stroke styles are solid.
 *
 * @author trejkaz
 */
public class DefaultAnnotationStyleScheme implements AnnotationStyleScheme {
    // Default styles.
    private static final AnnotationStyle defaultAnnotationStyle =
            new AnnotationStyle(new BasicStroke(), Color.RED, new Color(255, 240, 240));
    private static final AnnotationStyle defaultGroupAnnotationStyle =
            new AnnotationStyle(new BasicStroke(), Color.GRAY, new Color(240, 240, 240));

    private final AnnotationStyle annotationStyle;
    private final AnnotationStyle groupAnnotationStyle;

    /**
     * Constructs the style scheme with the default styles.
     */
    public DefaultAnnotationStyleScheme() {
        this(defaultAnnotationStyle, defaultGroupAnnotationStyle);
    }

    /**
     * Constructs the style scheme with specific styles.
     *
     * @param annotationStyle the style for the non-group annotations.
     * @param groupAnnotationStyle the style for the group annotations.
     */
    public DefaultAnnotationStyleScheme(AnnotationStyle annotationStyle, AnnotationStyle groupAnnotationStyle) {
        this.annotationStyle = annotationStyle;
        this.groupAnnotationStyle = groupAnnotationStyle;
    }

    @Override
    public AnnotationStyle getStyle(Annotation annotation) {
        if (annotation instanceof GroupAnnotation) {
            return groupAnnotationStyle;
        } else {
            return annotationStyle;
        }
    }
}
