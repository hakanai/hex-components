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
