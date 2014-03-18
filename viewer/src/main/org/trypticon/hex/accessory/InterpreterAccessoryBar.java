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

package org.trypticon.hex.accessory;

import org.trypticon.hex.HexViewer;
import org.trypticon.hex.interpreters.Interpreter;
import org.trypticon.hex.interpreters.InterpreterInfo;
import org.trypticon.hex.interpreters.MasterInterpreterStorage;
import org.trypticon.hex.interpreters.nulls.NullInterpreterInfo;
import org.trypticon.hex.util.swingsupport.NameRenderingComboBox;
import org.trypticon.hex.util.swingsupport.PLAFUtils;
import org.trypticon.hex.util.swingsupport.StealthFormattedTextField;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * An accessory bar which shows the interpretation of the selected bytes as a value.
 *
 * @author trejkaz
 */
public class InterpreterAccessoryBar extends AccessoryBar {
    private final ResourceBundle bundle = ResourceBundle.getBundle("org/trypticon/hex/Bundle");

    private final HexViewer viewer;
    private final JComboBox<InterpreterInfo> typeComboBox;
    private final JComboBox<?> byteOrderComboBox;
    private final JFormattedTextField valueTextField;

    public InterpreterAccessoryBar(HexViewer viewer) {
        this.viewer = viewer;


        List<InterpreterInfo> infos = filter(new MasterInterpreterStorage().getGroupedInterpreterInfos());
        typeComboBox = new NameRenderingComboBox<>(infos.toArray(new InterpreterInfo[infos.size()]));
        byteOrderComboBox = new JComboBox<>(new String[] {
                bundle.getString("AccessoryBars.Interpreter.byteOrderComboBox.big"),
                bundle.getString("AccessoryBars.Interpreter.byteOrderComboBox.little")
                });
        valueTextField = new StealthFormattedTextField(new ValueFormatterFactory());
        valueTextField.setEditable(false);

        SharedListener sharedListener = new SharedListener();
        selectedTypeChanged();
        typeComboBox.addItemListener(sharedListener);
        byteOrderComboBox.addItemListener(sharedListener);
        viewer.getSelectionModel().addChangeListener(sharedListener);
        viewer.addPropertyChangeListener("binary", sharedListener);
        valueTextField.addPropertyChangeListener("value", sharedListener);

        PLAFUtils.makeSmall(typeComboBox, byteOrderComboBox, valueTextField);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(4)
                .addComponent(typeComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(byteOrderComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valueTextField)
                .addGap(4));

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(typeComboBox)
                .addComponent(byteOrderComboBox)
                .addComponent(valueTextField));
    }

    private void selectedDataChanged() {
        updateInterpretedValue();
    }

    private void selectedTypeChanged() {
        updateByteOrderComboBox();
        updateInterpretedValue();
    }

    private void selectedByteOrderChanged() {
        updateInterpretedValue();
    }

    private void updateByteOrderComboBox() {
        InterpreterInfo info = (InterpreterInfo) typeComboBox.getSelectedItem();
        byteOrderComboBox.setVisible(interpreterHasByteOrder(info));
    }

    private void updateInterpretedValue() {
        InterpreterInfo info = (InterpreterInfo) typeComboBox.getSelectedItem();
        Map<String, Object> options = new HashMap<>(4);
        if (interpreterHasByteOrder(info)) {
            options.put("byteOrder", byteOrderComboBox.getSelectedIndex() == 0 ?
                                     ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        }
        Interpreter<?> interpreter = info.create(options);

        long selectionStart = viewer.getSelectionModel().getSelectionStart();
        long selectionEnd = viewer.getSelectionModel().getSelectionEnd();
        Object value;
        try {
            value = interpreter.interpret(viewer.getBinary(), selectionStart,
                                          selectionEnd - selectionStart + 1);
        } catch (IllegalArgumentException e) {
            valueTextField.setValue(bundle.getString("AccessoryBars.Interpreter.invalidSelection"));
            return;
        }

        valueTextField.setValue(value);
    }

    private boolean interpreterHasByteOrder(InterpreterInfo info) {
        for (InterpreterInfo.Option option : info.getOptions()) {
            // Can expand this as the UI grows to support more options.
            if ("byteOrder".equals(option.getName()) && ByteOrder.class.equals(option.getType())) {
                return true;
            }
        }
        return false;
    }

    private static List<InterpreterInfo> filter(List<InterpreterInfo> infos) {
        List<InterpreterInfo> result = new ArrayList<>(infos.size());
        for (InterpreterInfo info : infos) {
            if (info instanceof NullInterpreterInfo) {
                // Not much point to using this one.
                continue;
            }

            boolean usable = true;
            for (InterpreterInfo.Option option : info.getOptions()) {
                // Can expand this as the UI grows to support more options.
                if (!"byteOrder".equals(option.getName()) || !ByteOrder.class.equals(option.getType())) {
                    usable = false;
                    break;
                }
            }
            if (usable) {
                result.add(info);
            }
        }
        return result;
    }

    // All listeners in a single handler
    private class SharedListener implements PropertyChangeListener, ChangeListener, ItemListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            switch (event.getPropertyName()) {
                case "value":
                    ((JTextComponent) event.getSource()).setCaretPosition(0);
                    break;
                case "binary":
                    selectedDataChanged();
                    break;
            }
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            selectedDataChanged();
        }

        @Override
        public void itemStateChanged(ItemEvent event) {
            Object source = event.getSource();
            if (source == typeComboBox) {
                selectedTypeChanged();
            } else if (source == byteOrderComboBox) {
                selectedByteOrderChanged();
            }
        }
    }
}
