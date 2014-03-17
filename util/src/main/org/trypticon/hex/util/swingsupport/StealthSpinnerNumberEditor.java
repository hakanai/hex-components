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

package org.trypticon.hex.util.swingsupport;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

/**
 * A spinner editor which looks a lot like a label when it isn't being used to edit the value.
 */
public class StealthSpinnerNumberEditor extends JSpinner.NumberEditor {
    public StealthSpinnerNumberEditor(JSpinner spinner) {
        super(spinner);

        remove(getTextField());

        JFormattedTextField ftf = new StealthFormattedTextField();
        ftf.setName("Spinner.formattedTextField");
        ftf.setValue(spinner.getValue());
        ftf.addPropertyChangeListener(this);
        ftf.setEditable(false);
        ftf.setInheritsPopupMenu(true);

        String toolTipText = spinner.getToolTipText();
        if (toolTipText != null) {
            ftf.setToolTipText(toolTipText);
        }

        add(ftf);

    }
}
