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

package org.trypticon.hex.accessory;

import org.trypticon.hex.HexViewer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * An accessory bar consisting of a main {@link LocationAccessoryBar} along with controls to optionally
 * add and remove {@link InterpreterAccessoryBar}s.
 *
 * @author trejkaz
 */
public class ExpandableAccessoryBar extends AccessoryBar {
    private final HexViewer viewer;
    private int nextInterpreterIndex;

    public ExpandableAccessoryBar(HexViewer viewer) {
        this.viewer = viewer;
        add(new AccessoryBarWithButtons(new LocationAccessoryBar(viewer), false));
        setLayout(new GridLayout(0, 1));
    }

    @Override
    protected void preferencesNodeAttached(Preferences node) {
        removeAllOptionalBars();

        String[] childNames;
        try {
            childNames = node.childrenNames();
        } catch (BackingStoreException e) {
            // Not much we can do.
            Logger.getLogger(getClass().getName()).warning("Couldn't list child preference nodes: " + node);
            return;
        }

        int highestInterpreterIndex = -1;
        for (String childName : childNames) {
            Preferences childNode = node.node(childName);
            if ("location".equals(childName)) {
                AccessoryBarWithButtons bar = (AccessoryBarWithButtons) getComponent(0);
                bar.bar.setPreferencesNode(childNode);
            } else if (childName.startsWith("interpreter")) {
                int interpreterIndex = Integer.parseInt(childName.substring("interpreter".length()));
                highestInterpreterIndex = Math.max(highestInterpreterIndex, interpreterIndex);
                AccessoryBarWithButtons bar = new AccessoryBarWithButtons(new InterpreterAccessoryBar(viewer), true);
                bar.bar.setPreferencesNode(childNode);
                add(bar, 0);
            }
        }

        nextInterpreterIndex = highestInterpreterIndex + 1;

        // Make sure the existing location bar has a node set for it even if there wasn't one initially.
        AccessoryBarWithButtons bar = (AccessoryBarWithButtons) getComponent(0);
        if (bar.bar.getPreferencesNode() == null) {
            bar.bar.setPreferencesNode(node.node("location"));
        }

        revalidate();
    }

    @Override
    protected void preferencesNodeDetached(Preferences node) {
        for (Component component : getComponents()) {
            ((AccessoryBarWithButtons) component).bar.setPreferencesNode(null);
        }
    }

    private void addAccessoryBar() {
        int interpreterIndex = (nextInterpreterIndex++);
        Preferences node = getPreferencesNode();
        AccessoryBarWithButtons bar = new AccessoryBarWithButtons(new InterpreterAccessoryBar(viewer), true);
        if (node != null) {
            Preferences childNode = getPreferencesNode().node("interpreter" + interpreterIndex);
            bar.bar.setPreferencesNode(childNode);
        }
        add(bar, 0);
        revalidate();
    }

    private void removeAccessoryBar(AccessoryBarWithButtons bar) {
        Preferences node = bar.bar.getPreferencesNode();
        if (node != null) {
            bar.bar.setPreferencesNode(null);

            try {
                node.removeNode();
            } catch (BackingStoreException e) {
                // Not much we can do.
                Logger.getLogger(getClass().getName()).warning("Couldn't remove preference node: " + node);
            }
        }

        remove(bar);
        revalidate();
    }

    private void removeAllOptionalBars() {
        // The location bar is always last.
        while (getComponentCount() > 1) {
            AccessoryBarWithButtons bar = (AccessoryBarWithButtons) getComponent(0);
            bar.bar.setPreferencesNode(null);
            remove(bar);
        }
        revalidate();
    }

    private class AccessoryBarWithButtons extends JPanel implements ActionListener {
        private final AccessoryBar bar;
        private final JButton removeButton;
        private final JButton addButton;

        private AccessoryBarWithButtons(AccessoryBar bar, boolean canRemove) {
            this.bar = bar;

            // Metal L&F has a gradient to the bar and I didn't want it repeating over and over.
            bar.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
            bar.setOpaque(false);

            ResourceBundle bundle = ResourceBundle.getBundle("org/trypticon/hex/Bundle");

            removeButton = new JButton(new ImageIcon(getClass().getResource("Remove.png")));
            removeButton.setRolloverIcon(new ImageIcon(getClass().getResource("RemoveRollover.png")));
            removeButton.setPressedIcon(new ImageIcon(getClass().getResource("RemovePressed.png")));
            setCommonButtonProperties(removeButton);
            removeButton.setToolTipText(bundle.getString("AccessoryBars.remove.toolTip"));
            removeButton.setVisible(canRemove);

            addButton = new JButton(new ImageIcon(getClass().getResource("Add.png")));
            addButton.setRolloverIcon(new ImageIcon(getClass().getResource("AddRollover.png")));
            addButton.setPressedIcon(new ImageIcon(getClass().getResource("AddPressed.png")));
            setCommonButtonProperties(addButton);
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

        private void setCommonButtonProperties(JButton button) {
            button.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
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
