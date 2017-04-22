/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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
