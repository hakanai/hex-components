package org.trypticon.hex;

import java.awt.Paint;
import java.awt.Stroke;

/**
 * Encapsulates the style information for a single annotation.
 *
 * @author trejkaz
 */
public class AnnotationStyle {
    private final Stroke borderStroke;
    private final Paint borderPaint;
    private final Paint backgroundPaint;

    /**
     * Constructs the annotation style.
     *
     * @param borderStroke the border stroke.
     * @param borderPaint the border paint.
     * @param backgroundPaint the background paint.
     */
    public AnnotationStyle(Stroke borderStroke, Paint borderPaint, Paint backgroundPaint) {
        this.borderStroke = borderStroke;
        this.borderPaint = borderPaint;
        this.backgroundPaint = backgroundPaint;
    }

    /**
     * Gets the border stroke.
     *
     * @return the border stroke.
     */
    public Stroke getBorderStroke() {
        return borderStroke;
    }

    /**
     * Gets the border paint.
     *
     * @return the border paint.
     */
    public Paint getBorderPaint() {
        return borderPaint;
    }

    /**
     * Gets the background paint.
     *
     * @return the background paint.
     */
    public Paint getBackgroundPaint() {
        return backgroundPaint;
    }
}
