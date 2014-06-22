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

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Action to select the entire content of the viewer.
 *
 * @author trejkaz
 */
class SelectAllAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent event) {
        HexViewer viewer = (HexViewer) event.getSource();
        viewer.getSelectionModel().setSelection(0, viewer.getBinary().length() - 1);
    }
}
