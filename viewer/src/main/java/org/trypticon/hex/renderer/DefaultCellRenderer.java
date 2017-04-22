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

package org.trypticon.hex.renderer;

import org.trypticon.hex.AnnotationStyle;
import org.trypticon.hex.AnnotationStyleScheme;
import org.trypticon.hex.HexUtils;
import org.trypticon.hex.HexViewer;
import org.trypticon.hex.anno.Annotation;
import org.trypticon.hex.anno.AnnotationCollection;

import javax.swing.JLabel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.List;

/**
 * Default cell renderer implementation, using a Swing label as the component.
 *
 * @author trejkaz
 */
public class DefaultCellRenderer extends JLabel implements CellRenderer {
    private static final int ERROR_PLACEHOLDER = -1;

    private static final Color transparent = new Color(0, 0, 0, 0);

    private Paint backgroundPaint;

    public DefaultCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getRendererComponent(HexViewer viewer, boolean selected, boolean onCursorRow, boolean atCursor,
                                          long position, int valueDisplayMode) {

        // XXX: I should probably split this logic into different renderers for each column.
        //int charYOffset = (rowHeight - metrics.getAscent()) / 2;

        setFont(viewer.getFont());
        setBorder(null);

        setHorizontalAlignment(valueDisplayMode == ROW_OFFSET ? RIGHT : CENTER);

        Paint backgroundPaint = transparent;
        Color foreground;

        // XXX: This is redundant if rendering the address column.
        int b;
        try {
            b = viewer.getBinary().read(position) & 0xFF;
        } catch (Exception e) {
            // Eat the exception but mark it as an error
            b = ERROR_PLACEHOLDER;
        }

        if (valueDisplayMode == ROW_OFFSET) {
            foreground = viewer.getOffsetForeground();
        } else {
            if (b == ERROR_PLACEHOLDER) {
                foreground = viewer.getErrorForeground();
            } else {
                foreground = viewer.getForeground();
            }

            AnnotationCollection annotations = viewer.getAnnotations();
            AnnotationStyleScheme annotationStyleScheme = viewer.getAnnotationStyleScheme();
            int bytesPerRow = viewer.getBytesPerRow();

            List<? extends Annotation> annotationPath = annotations.getAnnotationPathAt(position);
            if (annotationPath != null) {
                Border border = null;

                for (Annotation annotation : annotationPath) {
                    long annoStart = annotation.getPosition();
                    long annoEnd = annoStart + annotation.getLength() - 1;

                    boolean top = position < annoStart + bytesPerRow;
                    boolean right = position == annoEnd && (position % bytesPerRow != bytesPerRow - 1 || top);
                    boolean bottom = position > annoEnd - bytesPerRow;
                    boolean left = position == annoStart && (position % bytesPerRow != 0 || bottom);

                    AnnotationStyle colours = annotationStyleScheme.getStyle(annotation);

                    if (top || right || bottom || left) {
                        Border nextBorder = new JointedLineBorder(colours.getBorderStroke(), colours.getBorderPaint(),
                                                                  top, right, bottom, left);
                        if (border == null) {
                            border = nextBorder;
                        } else if (border instanceof StackedBorder) {
                            ((StackedBorder) border).stack(nextBorder);
                        } else {
                            border = new StackedBorder(nextBorder);
                        }
                    }
                }

                if (border != null) {
                    setBorder(border);
                }

                AnnotationStyle colours = annotationStyleScheme.getStyle(
                        annotationPath.get(annotationPath.size() - 1));
                backgroundPaint = colours.getBackgroundPaint();
            }

            if (selected && viewer.getSelectionBackground() != null) {
                backgroundPaint = viewer.getSelectionBackground();
            }
            if (selected && viewer.getSelectionForeground() != null) {
                foreground = viewer.getSelectionForeground();
            }

            if (atCursor && viewer.getCursorBackground() != null) {
                backgroundPaint = viewer.getCursorBackground();
            }
            if (atCursor && viewer.getCursorForeground() != null) {
                foreground = viewer.getCursorForeground();
            }
        }

        this.backgroundPaint = backgroundPaint;
        setBackground(transparent); // so that the UI doesn't paint it
        setForeground(foreground);

        String str;

        switch (valueDisplayMode) {
            case ROW_OFFSET:
                String format = "%0" + viewer.getOffsetColumnDigits() + "x";
                str = String.format(format, position);
                break;
            case HEX:
                if (b == ERROR_PLACEHOLDER) {
                    str = "\u00D7\u00D7";
                } else {
                    str = HexUtils.toHex((byte) b);
                }
                break;
            case ASCII:
                if (b == ERROR_PLACEHOLDER) {
                    str = "\u00D7";
                } else {
                    str = HexUtils.toAscii((byte) b);
                }
                break;
            default:
                throw new IllegalStateException("Unimplemented display mode: " + valueDisplayMode);
        }

        setText(str);

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Paint the fancy background so that it's possible to use a Paint (setBackground only supports a Color.)
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(backgroundPaint);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }
}
