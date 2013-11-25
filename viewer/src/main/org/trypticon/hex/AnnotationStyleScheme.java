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
