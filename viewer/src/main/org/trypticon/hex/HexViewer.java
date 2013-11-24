/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

import org.trypticon.hex.anno.AnnotationCollection;
import org.trypticon.hex.anno.AnnotationCollectionEvent;
import org.trypticon.hex.anno.AnnotationCollectionListener;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.datatransfer.HexViewerTransferHandler;
import org.trypticon.hex.plaf.BasicHexViewerUI;
import org.trypticon.hex.plaf.HexViewerUI;
import org.trypticon.hex.renderer.CellRenderer;
import org.trypticon.hex.renderer.DefaultCellRenderer;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Swing widget for viewing binary in hexadecimal form.
 *
 * @author trejkaz
 */
public class HexViewer extends JComponent {
    /**
     * The number of bytes of binary which will be displayed per row.
     */
    private int bytesPerRow = 16;

    /**
     * The binary being viewed.
     */
    private Binary binary;

    /**
     * The collection of annotations to show.
     */
    private AnnotationCollection annotations;

    /**
     * The selection model, tracks where the selection and cursor are.
     */
    private final HexViewerSelectionModel selectionModel = new HexViewerSelectionModel();

    /**
     * The cell renderer.
     */
    private CellRenderer cellRenderer = new DefaultCellRenderer();

    // Colours.
    // XXX: These should probably come from UIDefaults.
    private Color offsetForeground = Color.GRAY;
    private Color cursorForeground = null;
    private Color cursorBackground = new Color(236, 235, 163);
    private Color cursorRowBackground = new Color(233, 239, 248);
    private Color selectionForeground = null;
    private Color selectionBackground = new Color(176, 197, 227);

    // Cached dimensions
    private int rowHeight;

    /**
     * This listener will repaint the viewer when the annotations change.
     */
    private AnnotationCollectionListener repaintListener;

    /**
     * The first visible row at the current scroll position.
     * This will be {@code -1} if you're positioned at the top, because of the blank line above the first row.
     */
    private long firstVisibleRow = -1;

    /**
     * The vertical scroll bar.
     */
    private JScrollBar verticalScrollBar;

    /**
     * Takes care of syncing the view with the scroll bar.
     */
    private ScrollBarSync scrollBarSync;

    /**
     * Constructs the hex viewer.
     */
    public HexViewer() {
        setUI(new BasicHexViewerUI());
        setFont(new Font("Courier New", Font.PLAIN, 12));
        setFocusable(true);
        setTransferHandler(new HexViewerTransferHandler());

        selectionModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                repaint();
            }
        });

        verticalScrollBar = new JScrollBar(JScrollBar.VERTICAL);
        scrollBarSync = new ScrollBarSync(this, verticalScrollBar);
        add(verticalScrollBar);

        setOpaque(true);
        updateUI();
    }

    /**
     * Gets the UI for pluggable look and feel of this component.
     *
     * @return the UI.
     */
    public HexViewerUI getUI() {
        return (HexViewerUI) ui;
    }

    /**
     * Sets the UI for pluggable look and feel of this component.
     *
     * @param ui the UI.
     */
    public void setUI(HexViewerUI ui) {
        super.setUI(ui);
    }

    @Override
    public void updateUI()
    {
        setBackground(UIManager.getColor("TextArea.background"));
    }

    /**
     * Gets the binary being viewed.
     *
     * @return the binary being viewed.
     */
    public Binary getBinary() {
        return binary;
    }

    /**
     * Sets the binary being viewed.
     *
     * @param binary the binary being viewed.
     */
    public void setBinary(Binary binary) {
        this.binary = binary;

        selectionModel.setCursor(0);
        selectionModel.setCursorAndExtendSelection(0);

        invalidate();
        repaint();
    }

    /**
     * Gets the collection of annotations to show.
     *
     * @return the annotations to show.
     */
    public AnnotationCollection getAnnotations() {
        return annotations;
    }

    /**
     * Sets the collection of annotations to show.
     *
     * @param annotations the annotations to show.
     */
    public void setAnnotations(AnnotationCollection annotations) {
        if (this.annotations != null) {
            this.annotations.removeAnnotationCollectionListener(repaintListener);
        }

        this.annotations = annotations;
        repaint();

        if (annotations != null) {
            if (repaintListener == null) {
                repaintListener = new AnnotationCollectionListener() {
                    @Override
                    public void annotationsChanged(AnnotationCollectionEvent event) {
                        repaint();
                    }
                };
            }

            annotations.addAnnotationCollectionListener(repaintListener);
        }
    }

    /**
     * Gets the number of bytes displayed per row.
     *
     * @return the number of bytes displayed per row.
     */
    public int getBytesPerRow() {
        return bytesPerRow;
    }

    /**
     * Sets the number of bytes displayed per row. Generally this should be a multiple of 8, ideally a multiple of 16.
     *
     * @param bytesPerRow the number of bytes displayed per row.
     */
    public void setBytesPerRow(int bytesPerRow) {
        this.bytesPerRow = bytesPerRow;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);

        // Need to update the precomputed values...
        FontMetrics metrics = getFontMetrics(font);
        rowHeight = metrics.getHeight();
    }

    /**
     * Gets the colour used to paint the offsets in the left column of the view.
     *
     * @return the offset foreground.
     */
    public Color getOffsetForeground() {
        return offsetForeground;
    }

    /**
     * Sets the colour used to paint the offsets in the left column of the view.
     *
     * @param offsetForeground the offset foreground.
     */
    public void setOffsetForeground(Color offsetForeground) {
        this.offsetForeground = offsetForeground;
    }

    /**
     * Gets the colour used to paint the cursor foreground.  May be {@code null}, in which case the default foreground
     * colour will be used.
     *
     * @return the cursor foreground.
     */
    public Color getCursorForeground() {
        return cursorForeground;
    }

    /**
     * Sets the colour used to paint the cursor foreground.  May be {@code null}, in which case the default foreground
     * colour will be used.
     *
     * @param cursorForeground the cursor foreground.
     */
    public void setCursorForeground(Color cursorForeground) {
        this.cursorForeground = cursorForeground;
    }

    /**
     * Gets the colour used to paint the cursor background.
     *
     * @return the cursor background.
     */
    public Color getCursorBackground() {
        return cursorBackground;
    }

    /**
     * Sets the colour used to paint the cursor background.
     *
     * @param cursorBackground the cursor background.
     */
    public void setCursorBackground(Color cursorBackground) {
        this.cursorBackground = cursorBackground;
    }

    /**
     * Gets the colour used to paint the background for the row in which the cursor is positioned.
     *
     * @return the cursor row background.
     */
    public Color getCursorRowBackground() {
        return cursorRowBackground;
    }

    /**
     * Sets the colour used to paint the background for the row in which the cursor is positioned.
     *
     * @param cursorRowBackground the cursor row background.
     */
    public void setCursorRowBackground(Color cursorRowBackground) {
        this.cursorRowBackground = cursorRowBackground;
    }

    /**
     * Gets the colour used to paint the selection foreground.  May be {@code null}, in which case the default
     * foreground colour will be used.
     *
     * @return the selection foreground.
     */
    public Color getSelectionForeground() {
        return selectionForeground;
    }

    /**
     * Sets the colour used to paint the selection foreground.  May be {@code null}, in which case the default
     * foreground colour will be used.
     *
     * @param selectionForeground the selection foreground.
     */
    public void setSelectionForeground(Color selectionForeground) {
        this.selectionForeground = selectionForeground;
    }

    /**
     * Gets the colour used to paint the selection background.
     *
     * @return the selection background.
     */
    public Color getSelectionBackground() {
        return selectionBackground;
    }

    /**
     * Sets the colour used to paint the selection background.
     *
     * @param selectionBackground the selection background.
     */
    public void setSelectionBackground(Color selectionBackground) {
        this.selectionBackground = selectionBackground;
    }

    /**
     * Gets the selection model.  The selection model can be used to determine the location of the selected region of
     * binary, and the position of the cursor.
     *
     * @return the selection model.
     */
    public HexViewerSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Gets the cell renderer.
     *
     * @return the cell renderer.
     */
    public CellRenderer getCellRenderer() {
        return cellRenderer;
    }

    /**
     * Gets the row height.  This is indirectly determined from the font.
     *
     * @return the row height.
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * Gets the first visible row.
     *
     * @return the first visible row.
     */
    public long getFirstVisibleRow() {
        return firstVisibleRow;
    }

    /**
     * Sets the first visible row.
     *
     * @param firstVisibleRow the first visible row.
     */
    public void setFirstVisibleRow(long firstVisibleRow) {
        this.firstVisibleRow = firstVisibleRow;

        repaint();
        scrollBarSync.updateScrollBarFromPosition();
    }

    /**
     * Gets the number of rows visible in the viewer.
     * Includes the partial row at the end, if one is present.
     *
     * @return the visible row count.
     */
    public int getVisibleRowCount() {
        return (getHeight() - 1) / rowHeight + 1;
    }

    /**
     * Gets the number of rows of binary present (not just the number visible.)
     *
     * @return the row count.
     */
    public long getRowCount() {
        if (binary == null) {
            return 0;
        }
        return ((binary.length() - 1) / bytesPerRow + 1);
    }

    /**
     * Scrolls the view to make the given byte position visible.
     *
     * @param pos the position.
     */
    public void scrollPosToVisible(long pos) {
        if (pos < 0 || pos >= binary.length()) {
            throw new IllegalArgumentException("Position out of bounds: " + pos);
        }

        long firstVisibleRow = getFirstVisibleRow();
        int visibleRowCount = getVisibleRowCount();
        long lastVisibleRow = firstVisibleRow + visibleRowCount - 2; // disregard the partial row

        long row = pos / bytesPerRow;

        // Scroll the shortest distance possible to make it visible.
        if (row < firstVisibleRow) {
            setFirstVisibleRow(row);
        } else if (row > lastVisibleRow) {
            setFirstVisibleRow(row - visibleRowCount + 1);
        } // else already visible
    }

    /**
     * Gets the bounds for the given position within the binary.  This will match up to where we draw the background for
     * selected bytes.
     *
     * @param pos the position.
     * @return the bounds. Warning: the bounds will only make sense if the position you pass is visible or nearby.
     *         If you pass a position which is very far away, you may overflow the limit of the size of an integer.
     */
    public Rectangle getBoundsForPosition(long pos) {
        return getUI().modelToView(this, pos);
    }

    /**
     * Gets the position within the binary for the specified point within the panel.
     *
     * @param point the point.
     * @return the position, or {@code -1} if it is not in range.
     */
    public long getPositionForPoint(Point point) {
        return getUI().viewToModel(this, point);
    }


    @Override
    public void paintComponent(Graphics g) {
        getUI().paint(g, this);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int scrollBarWidth = verticalScrollBar.getPreferredSize().width;
        verticalScrollBar.setBounds(getWidth() - scrollBarWidth, 0, scrollBarWidth, getHeight());
        scrollBarSync.updateScrollBarFromView();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getPreferredWidth(), getPreferredHeight(20));
    }

    private int getPreferredWidth() {
        FontMetrics metrics = getFontMetrics(getFont());
        return metrics.charWidth('D') *
                (3 + 8 + 1 + 1 + (bytesPerRow * 3) + 2 + 16 + 3) +
                verticalScrollBar.getPreferredSize().width;
    }

    private int getPreferredHeight(int numRows) {
        FontMetrics metrics = getFontMetrics(getFont());
        return metrics.getHeight() * (numRows + 2);
    }

    private int getPreferredHeight() {
        return getPreferredHeight(binary == null ? 20 : (int) getRowCount());
    }
}
