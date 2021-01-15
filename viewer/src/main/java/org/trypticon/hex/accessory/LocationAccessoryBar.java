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

package org.trypticon.hex.accessory;

import org.trypticon.hex.HexViewer;
import org.trypticon.hex.HexViewerSelectionModel;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.util.swingsupport.GuiLocale;
import org.trypticon.hex.util.swingsupport.PLAFUtils;
import org.trypticon.hex.util.swingsupport.StealthFormattedTextField;
import org.trypticon.hex.util.swingsupport.StealthSpinner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * An accessory bar showing the location within the file and allowing changing it.
 *
 * @author trejkaz
 */
// Swing's own guidelines say not to use serialisation.
@SuppressWarnings("serial")
public class LocationAccessoryBar extends AccessoryBar {
    private final HexViewer viewer;
    private final JSpinner columnsSpinner;
    private final CustomHexFormattedTextField offsetField;
    private final CustomHexFormattedTextField lengthField;
    private final Handler handler;

    @Nullable
    private Preferences preferencesNode;

    public LocationAccessoryBar(HexViewer viewer) {
        this.viewer = viewer;
        handler = new Handler();

        ResourceBundle bundle = ResourceBundle.getBundle("org/trypticon/hex/Bundle", GuiLocale.get());
        JLabel columnsLabel = new JLabel(bundle.getString("AccessoryBars.Location.columns"));
        columnsSpinner = new StealthSpinner(new SpinnerNumberModel(16, 8, 64, 8));
        JLabel offsetLabel = new JLabel(bundle.getString("AccessoryBars.Location.offset"));
        offsetField = new CustomHexFormattedTextField();
        JLabel lengthLabel = new JLabel(bundle.getString("AccessoryBars.Location.length"));
        lengthField = new CustomHexFormattedTextField();

        JTextField columnsSpinnerTextField = ((JSpinner.DefaultEditor) columnsSpinner.getEditor()).getTextField();
        columnsSpinner.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                userChangedColumns();
            }
        });

        PLAFUtils.makeSmall(columnsLabel, columnsSpinner, columnsSpinnerTextField,
                            offsetLabel, offsetField, lengthLabel, lengthField);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(4)
                .addComponent(columnsLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(columnsSpinner, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offsetLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offsetField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lengthLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lengthField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(columnsLabel)
                .addComponent(columnsSpinner)
                .addComponent(offsetLabel)
                .addComponent(offsetField)
                .addComponent(lengthLabel)
                .addComponent(lengthField));
    }

    @Override
    protected void preferencesNodeAttached(Preferences node) {
        preferencesNode = node;

        int columns = node.getInt("columns", 16);
        columnsSpinner.setValue(columns);
    }

    @Override
    protected void preferencesNodeDetached(Preferences node) {
        preferencesNode = node;
    }

    /**
     * Moves focus to the columns field.
     */
    public void focusColumnsField() {
        JTextField textField = ((JSpinner.NumberEditor) columnsSpinner.getEditor()).getTextField();
        textField.selectAll();
        textField.requestFocusInWindow();
    }

    /**
     * Moves focus to the offset field.
     */
    public void focusOffsetField() {
        offsetField.selectAll();
        offsetField.requestFocusInWindow();
    }

    /**
     * Moves focus to the length field.
     */
    public void focusLengthField() {
        lengthField.selectAll();
        lengthField.requestFocusInWindow();
    }

    private void binaryChanged() {
        Binary binary = viewer.getBinary();
        long maxLength = binary != null ? binary.length() : 1;
        offsetField.updateMaxValue(maxLength);
        lengthField.updateMaxValue(maxLength);
        // updateMaxValue does invalidate the fields but we still have to manually trigger validation.
        revalidate();
    }

    private void locationChanged() {
        HexViewerSelectionModel selectionModel = viewer.getSelectionModel();
        long selectionStart = selectionModel.getSelectionStart();
        long selectionEnd = selectionModel.getSelectionEnd();
        offsetField.setValue(selectionStart);
        offsetField.setCaretPosition(0);
        lengthField.setValue(selectionEnd - selectionStart + 1);
        lengthField.setCaretPosition(0);
    }

    private void userChangedColumns() {
        int columns = ((Number) columnsSpinner.getValue()).intValue();
        viewer.setBytesPerRow(columns);
        if (preferencesNode != null) {
            preferencesNode.putInt("columns", columns);
        }
    }

    private void userChangedLocation() {
        long offset = ((Number) offsetField.getValue()).longValue();
        long length = ((Number) lengthField.getValue()).longValue();

        Binary binary = viewer.getBinary();

        if (offset < 0 || binary == null) {
            offset = 0;
        } else if (offset >= binary.length()) {
            offset = binary.length() - 1;
        }

        if (length < 1 || binary == null) {
            length = 1;
        } else if (offset + length >= binary.length() - 1) {
            length = binary.length() - offset;
        }

        HexViewerSelectionModel selectionModel = viewer.getSelectionModel();
        selectionModel.setSelection(offset, offset + length - 1);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        viewer.addPropertyChangeListener("binary", handler);
        viewer.getSelectionModel().addChangeListener(handler);
        binaryChanged();
        locationChanged();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        viewer.removePropertyChangeListener("binary", handler);
    }

    private class Handler implements PropertyChangeListener, ChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            switch (event.getPropertyName()) {
                case "binary":
                    binaryChanged();
                    break;
            }
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            locationChanged();
        }
    }

    private class CustomHexFormattedTextField extends StealthFormattedTextField {
        private long maxValue = 1;

        // setFormatter() appears to have no effect. Swing uses its own default number formatter.
        CustomHexFormattedTextField() {
            super(new AbstractFormatterFactory() {
                @Override
                public AbstractFormatter getFormatter(JFormattedTextField tf) {
                    return new AbstractFormatter() {
                        @Override
                        @Nullable
                        public Object stringToValue(String text) throws ParseException {
                            text = text.trim();
                            if (text.isEmpty()) {
                                return null;
                            }
                            try {
                                return Long.decode(text);
                            } catch (NumberFormatException e) {
                                throw new ParseException(text, 0);
                            }
                        }

                        @Override
                        @Nonnull
                        public String valueToString(Object value) throws ParseException {
                            if (value == null) {
                                return "";
                            }
                            return "0x" + Long.toString(((Number) value).longValue(), 16);
                        }
                    };
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            if (isPreferredSizeSet()) {
                return size;
            }

            // JTextField's columns are computed in terms of 'm' which makes it too wide on some fonts.
            StringBuilder longestString;
            try {
                longestString = new StringBuilder(getFormatter().valueToString(maxValue));
            } catch (ParseException e) {
                throw new RuntimeException("Unexpected error converting to string", e);
            }

            FontMetrics metrics = getFontMetrics(getFont());

            int widthOfWidestDigit = 0;
            for (int value = 0; value < 16; value++) {
                String string;
                try {
                    string = getFormatter().valueToString(value);
                } catch (ParseException e) {
                    throw new RuntimeException("Unexpected error converting to string", e);
                }
                string = string.substring(2); // chopping off "0x"
                widthOfWidestDigit = Math.max(widthOfWidestDigit, metrics.stringWidth(string));
            }

            int numDigits = longestString.length() - 2; // chopping off "0x"
            // The widthOfWidestDigit / 2 here is to give breathing room for placing the caret after the value.
            int longestValueWidth = metrics.stringWidth("0x") + numDigits * widthOfWidestDigit + widthOfWidestDigit / 2;

            Insets insets = getInsets();
            size.width = longestValueWidth + insets.left + insets.right;
            return size;
        }

        @Override
        public void commitEdit() throws ParseException {
            super.commitEdit();
            userChangedLocation();
            if (hasFocus()) {
                selectAll();
            }
        }

        private void updateMaxValue(long maxValue) {
            this.maxValue = maxValue;
            invalidate();
        }
    }
}
