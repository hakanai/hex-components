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

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A text field for displaying values which looks a lot like a label when it isn't being used to edit text.
 */
public class StealthFormattedTextField extends JFormattedTextField {
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private JLabel labelTemplate;
    private JTextField textFieldTemplate;

    public StealthFormattedTextField() {
        commonInit();
    }

    public StealthFormattedTextField(AbstractFormatterFactory factory) {
        super(factory);
        commonInit();
    }

    private void commonInit() {
        addPropertyChangeListener("JComponent.sizeVariant", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                updateProperties();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
                updateProperties();
            }

            @Override
            public void focusLost(FocusEvent event) {
                updateProperties();
            }
        });
        updateUI();
    }

    private void updateProperties() {
        labelTemplate.putClientProperty("JComponent.sizeVariant", getClientProperty("JComponent.sizeVariant"));
        textFieldTemplate.putClientProperty("JComponent.sizeVariant", getClientProperty("JComponent.sizeVariant"));

        // Make the label insets the same as the text field to stop the UI jumping around every time the state changes.
        Insets insets = textFieldTemplate.getBorder().getBorderInsets(this);
        labelTemplate.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));

        setFont(labelTemplate.getFont());
        if (hasFocus()) {
            textFieldTemplate.setEditable(isEditable());
            setOpaque(true);
            setBorder(textFieldTemplate.getBorder());
            setForeground(textFieldTemplate.getForeground());
            setBackground(textFieldTemplate.getBackground());
        } else {
            setOpaque(false);
            setBorder(labelTemplate.getBorder());
            setForeground(labelTemplate.getForeground());
            setBackground(TRANSPARENT);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();

        labelTemplate = new JLabel();
        textFieldTemplate = new JTextField();

        updateProperties();
    }
}
