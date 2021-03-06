/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
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

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Action to select the entire content of the viewer.
 *
 * @author trejkaz
 */
// Swing's own guidelines say not to use serialisation.
@SuppressWarnings("serial")
class SelectAllAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent event) {
        HexViewer viewer = (HexViewer) event.getSource();
        Binary binary = viewer.getBinary();
        if (binary == null) {
            return;
        }

        viewer.getSelectionModel().setSelection(0, binary.length() - 1);
    }
}
