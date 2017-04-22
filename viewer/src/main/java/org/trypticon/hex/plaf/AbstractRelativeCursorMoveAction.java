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

package org.trypticon.hex.plaf;

import org.trypticon.hex.HexViewer;
import org.trypticon.hex.binary.Binary;

import javax.annotation.Nonnull;

/**
 * Base class for actions which move the cursor.
 *
 * @author trejkaz
 */
abstract class AbstractRelativeCursorMoveAction extends AbstractCursorMoveAction {
    @Override
    protected long getNewCursorPos(@Nonnull HexViewer viewer, @Nonnull Binary binary) {
        return viewer.getSelectionModel().getCursor() + getShift(viewer);
    }

    /**
     * Gets the amount by which the cursor should shift when performing this action (positive is forwards, negative is
     * backwards.)  The value returned can bring the cursor to an illegal position; this is properly checked before
     * actually setting the position.
     *
     * @param viewer the hex viewer.
     * @return the amount by which the cursor should shift.
     */
    protected abstract int getShift(@Nonnull HexViewer viewer);

}
