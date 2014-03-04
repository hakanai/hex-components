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

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * A text field for displaying values which looks a lot like a label when it isn't being used to edit text.
 */
public class StealthFormattedTextField extends JFormattedTextField {
    JLabel labelTemplate;
    JTextField textFieldTemplate;

    public StealthFormattedTextField() {
        commonInit();
    }

    private void commonInit() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                updateBorder();
            }

            @Override
            public void focusLost(FocusEvent event) {
                updateBorder();
            }
        });
        updateUI();
    }

    private void updateBorder() {
        if (hasFocus()) {
            textFieldTemplate.setEditable(isEditable());
            setBorder(textFieldTemplate.getBorder());
            setForeground(textFieldTemplate.getForeground());
            setBackground(textFieldTemplate.getBackground());
        } else {
            setBorder(labelTemplate.getBorder());
            setForeground(labelTemplate.getForeground());
            setBackground(labelTemplate.getBackground());
        }
        getParent().revalidate();
    }

    @Override
    public void updateUI() {
        super.updateUI();

        labelTemplate = new JLabel();
        textFieldTemplate = new JTextField();

        Insets insets = textFieldTemplate.getBorder().getBorderInsets(this);
        labelTemplate.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));

        setFont(labelTemplate.getFont());
        setForeground(labelTemplate.getForeground());
        setBackground(labelTemplate.getBackground());
        setBorder(labelTemplate.getBorder());
    }
}
