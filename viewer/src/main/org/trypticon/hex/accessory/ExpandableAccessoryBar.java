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

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * An accessory bar consisting of a main {@link LocationAccessoryBar} along with controls to optionally
 * add and remove {@link InterpreterAccessoryBar}s.
 *
 * @author trejkaz
 */
public class ExpandableAccessoryBar extends AccessoryBar {
    private final HexViewer viewer;

    public ExpandableAccessoryBar(HexViewer viewer) {
        this.viewer = viewer;
        add(new AccessoryBarWithButtons(new LocationAccessoryBar(viewer), false));
        setLayout(new GridLayout(0, 1));
    }

    private void addAccessoryBar() {
        add(new AccessoryBarWithButtons(new InterpreterAccessoryBar(viewer), true), 0);
        revalidate();
    }

    private void removeAccessoryBar(AccessoryBarWithButtons bar) {
        remove(bar);
        revalidate();
    }

    private class AccessoryBarWithButtons extends JPanel implements ActionListener {
        private final AccessoryBar bar;
        private final JButton removeButton;
        private final JButton addButton;

        private AccessoryBarWithButtons(AccessoryBar bar, boolean canRemove) {
            this.bar = bar;

            ResourceBundle bundle = ResourceBundle.getBundle("org/trypticon/hex/Bundle");

            removeButton = new JButton(new ImageIcon(getClass().getResource("Remove.png")));
            removeButton.setRolloverIcon(new ImageIcon(getClass().getResource("RemoveRollover.png")));
            removeButton.setPressedIcon(new ImageIcon(getClass().getResource("RemovePressed.png")));
            removeButton.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            removeButton.setBorderPainted(false);
            removeButton.setToolTipText(bundle.getString("AccessoryBars.remove.toolTip"));
            removeButton.setVisible(canRemove);

            addButton = new JButton(new ImageIcon(getClass().getResource("Add.png")));
            addButton.setRolloverIcon(new ImageIcon(getClass().getResource("AddRollover.png")));
            addButton.setPressedIcon(new ImageIcon(getClass().getResource("AddPressed.png")));
            addButton.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            addButton.setBorderPainted(false);
            addButton.setToolTipText(bundle.getString("AccessoryBars.add.toolTip"));

            removeButton.addActionListener(this);
            addButton.addActionListener(this);

            GroupLayout layout = new GroupLayout(this);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(bar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(removeButton)
                    .addComponent(addButton));

            layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(bar)
                    .addComponent(removeButton)
                    .addComponent(addButton));

            setLayout(layout);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == addButton) {
                addAccessoryBar();
            } else { // must be removeButton
                removeAccessoryBar(this);
            }
        }
    }

}
