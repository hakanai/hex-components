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

package org.trypticon.hex.util.swingsupport;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A button showing the selected encoding. Clicking the button pops up the selection dialog.
 *
 * @author trejkaz
 */
public class SelectEncodingButton extends JButton {
    private Charset encoding;

    public SelectEncodingButton() {
        setEncoding(StandardCharsets.UTF_8);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SelectEncodingPane pane = new SelectEncodingPane();
                Charset selected = pane.showDialog(getRootPane());
                if (selected != null) {
                    setEncoding(selected);
                }
            }
        });
    }

    /**
     * Gets the current encoding.
     *
     * @return the encoding.
     */
    public Charset getEncoding() {
        return encoding;
    }

    /**
     * Sets a new encoding.
     *
     * @param encoding the encoding.
     */
    public void setEncoding(Charset encoding) {
        setText(encoding.name());
        Charset oldEncoding = this.encoding;
        this.encoding = encoding;
        firePropertyChange("encoding", oldEncoding, encoding);
    }
}
