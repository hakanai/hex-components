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
 * Action to move the selection to the end of the line.
 *
 * @author trejkaz
 */
class SelectionLineEndAction extends AbstractSelectionMoveAction {
    @Override
    protected long getNewCursorPos(@Nonnull HexViewer viewer, @Nonnull Binary binary) {
        return (viewer.getSelectionModel().getCursor() / viewer.getBytesPerRow()) * viewer.getBytesPerRow()
                + viewer.getBytesPerRow() - 1;
    }
}
