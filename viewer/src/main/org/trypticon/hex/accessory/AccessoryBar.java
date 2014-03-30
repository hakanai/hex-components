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

import javax.swing.JToolBar;
import java.util.prefs.Preferences;

/**
 * A toolbar showing some additional information.
 *
 * @author trejkaz
 */
public abstract class AccessoryBar extends JToolBar {
    private Preferences preferencesNode;

    public AccessoryBar() {
        setFloatable(false);
    }

    /**
     * Gets the preferences node settings are recorded under.
     *
     * @return the preferences node.
     */
    public final Preferences getPreferencesNode() {
        return preferencesNode;
    }

    /**
     * Sets a preferences node to use to record the settings. On setting, any preferences stored in the node
     * immediately apply to the accessory bar. If a preferences node is set, then on changing the settings
     * on the accessory bar, the preferences node will be updated with the new settings.
     *
     * @param node the preferences node to synchronise with.
     */
    public final void setPreferencesNode(Preferences node) {
        if (preferencesNode != null) {
            preferencesNodeDetached(preferencesNode);
        }
        preferencesNode = node;
        if (node != null) {
            preferencesNodeAttached(node);
        }
    }

    /**
     * Called when a preferences node has been attached.
     * Preferences should be loaded from the node and listeners should be added to update preferences.
     *
     * @param node the preferences node which was just attached.
     */
    protected abstract void preferencesNodeAttached(Preferences node);

    /**
     * Called when a preferences node has been detached.
     * Listeners which were previously added to update preferences should be removed.
     *
     * @param node the preferences node which was just detached.
     */
    protected abstract void preferencesNodeDetached(Preferences node);
}
