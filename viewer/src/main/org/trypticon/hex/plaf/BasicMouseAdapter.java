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

package org.trypticon.hex.plaf;

import org.trypticon.hex.HexViewer;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Handles mouse events on the viewer.
 *
 * @author trejkaz
 */
class BasicMouseAdapter extends MouseInputAdapter {

    @Override
    public void mousePressed(MouseEvent event) {
        HexViewer viewer = (HexViewer) event.getSource();
        viewer.requestFocusInWindow();

        if (javax.swing.SwingUtilities.isLeftMouseButton(event)) {
            // TODO: If it's within the address lines it might be better to select
            //       the row and then have dragging select further rows.

            long pos = viewer.getPositionForPoint(event.getPoint());
            viewer.getSelectionModel().setCursor(pos);
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (javax.swing.SwingUtilities.isLeftMouseButton(event)) {
            HexViewer viewer = (HexViewer) event.getSource();

            long pos = viewer.getPositionForPoint(event.getPoint());
            viewer.getSelectionModel().setCursorAndExtendSelection(pos);

            // TODO: An option for disabling autoscroll on selection would
            //       fit with the rest of Swing but I don't need it immediately.

            viewer.scrollPosToVisible(pos);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        HexViewer viewer = (HexViewer) event.getSource();

        long firstVisibleRow = viewer.getFirstVisibleRow();
        int visibleRowCount = viewer.getVisibleRowCount();

        // TODO: Support precise scrolling. Unfortunately this is rather fiddly because we already have to
        //       deal with the number of lines offset and this would add an additional fractional value.
        int moveRows = event.getWheelRotation();
        switch (event.getScrollType()) {
            case MouseWheelEvent.WHEEL_BLOCK_SCROLL:
                moveRows *= (visibleRowCount - 1);
                break;
            case MouseWheelEvent.WHEEL_UNIT_SCROLL:
                moveRows *= event.getScrollAmount();
                break;
            default:
                return;
        }

        long row = firstVisibleRow;
        row += moveRows;
        viewer.setFirstVisibleRow(row);
    }
}
