/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Trejkaz, Hex Project
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

import org.jdesktop.swingx.JXCollapsiblePane;
import org.trypticon.hex.HexViewer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
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
// Swing's own guidelines say not to use serialisation.
@SuppressWarnings("serial")
public class ExpandableAccessoryBar extends AccessoryBar {
    private final HexViewer viewer;
    private int nextInterpreterIndex;

    public ExpandableAccessoryBar(HexViewer viewer) {
        this.viewer = viewer;
        add(new AccessoryBarWithButtons(new LocationAccessoryBar(viewer), false));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(true);
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
    }

    @Override
    protected void preferencesNodeDetached(Preferences node) {
        for (Component component : getComponents()) {
            ((AccessoryBarWithButtons) component).bar.setPreferencesNode(null);
        }
    }

    /**
     * Gets the first accessory bar of the given class.
     *
     * @param barClass the accessory bar class.
     * @param <B> the accessory bar class.
     * @return the first accessory bar of that class. Returns {@code null} if there isn't one.
     */
    public <B extends AccessoryBar> B getFirstAccessoryBar(Class<B> barClass) {
        for (Component component : getComponents()) {
            AccessoryBar bar = ((AccessoryBarWithButtons) component).bar;
            if (barClass.isInstance(bar)) {
                return barClass.cast(bar);
            }
        }
        return null;
    }

    /**
     * Gets all child accessory bars of the given class.
     *
     * @param barClass the accessory bar class.
     * @param <B> the accessory bar class.
     * @return all accessory bars of that class.
     */
    public <B extends AccessoryBar> List<B> getAccessoryBars(Class<B> barClass) {
        List<B> results = new LinkedList<>();
        for (Component component : getComponents()) {
            AccessoryBar bar = ((AccessoryBarWithButtons) component).bar;
            if (barClass.isInstance(bar)) {
                results.add(barClass.cast(bar));
            }
        }
        return results;
    }

    private void addAccessoryBar() {
        int interpreterIndex = (nextInterpreterIndex++);
        Preferences node = getPreferencesNode();
        AccessoryBarWithButtons bar = new AccessoryBarWithButtons(new InterpreterAccessoryBar(viewer), true);
        bar.setDirection(JXCollapsiblePane.Direction.END);
        bar.setCollapsed(true);
        if (node != null) {
            Preferences childNode = getPreferencesNode().node("interpreter" + interpreterIndex);
            bar.bar.setPreferencesNode(childNode);
        }
        add(bar, 0);
        bar.setCollapsed(false);
    }

    private void removeAccessoryBar(final AccessoryBarWithButtons bar) {
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

        // setCollapsed will animate it to collapsed, but we want to know when it is finished so we can remove it.
        bar.addPropertyChangeListener("collapsed", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                remove(bar);
            }
        });
        bar.setCollapsed(true);
    }

    private void removeAllOptionalBars() {
        // The location bar is always last.
        while (getComponentCount() > 1) {
            AccessoryBarWithButtons bar = (AccessoryBarWithButtons) getComponent(0);
            bar.bar.setPreferencesNode(null);
            remove(bar);
        }
    }

    private class AccessoryBarWithButtons extends JXCollapsiblePane implements ActionListener {
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

            JPanel wrapper = new JPanel();
            GroupLayout layout = new GroupLayout(wrapper);
            wrapper.setLayout(layout);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(bar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(removeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

            layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(bar)
                    .addComponent(removeButton)
                    .addComponent(addButton));

            setLayout(new BorderLayout());
            add(wrapper, BorderLayout.CENTER);
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
