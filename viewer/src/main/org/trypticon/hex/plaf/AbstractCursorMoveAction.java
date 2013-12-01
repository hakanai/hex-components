/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2013  Trejkaz, Hex Project
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

import javax.swing.AbstractAction;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

/**
 * Base class for actions which move the cursor.
 *
 * @author trejkaz
 */
abstract class AbstractCursorMoveAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent event) {
        HexViewer viewer = (HexViewer) event.getSource();

        long newCursorPos = getNewCursorPos(viewer);
        long length = viewer.getBinary().length();

        if (newCursorPos >= length) {
            newCursorPos = length - 1;
            Toolkit.getDefaultToolkit().beep();
        } else if (newCursorPos < 0) {
            newCursorPos = 0;
            Toolkit.getDefaultToolkit().beep();
        }

        moveCursorTo(viewer, newCursorPos);

        viewer.scrollPosToVisible(newCursorPos);
    }

    /**
     * Gets the new cursor position.
     *
     * @param viewer the hex viewer.
     * @return the new (absolute) cursor position.
     */
    protected abstract long getNewCursorPos(HexViewer viewer);

    /**
     * Called to move the cursor.
     *
     * @param viewer the hex viewer.
     * @param newCursorPos the new cursor position.
     */
    void moveCursorTo(HexViewer viewer, long newCursorPos) {
        viewer.getSelectionModel().setCursor(newCursorPos);
    }
}
