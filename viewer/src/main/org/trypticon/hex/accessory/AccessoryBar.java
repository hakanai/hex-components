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
    public AccessoryBar() {
        setFloatable(false);
    }

    /**
     * Sets a preferences node to use to record the settings. On setting, any preferences stored in the node
     * immediately apply to the accessory bar. If a preferences node is set, then on changing the settings
     * on the accessory bar, the preferences node will be updated with the new settings.
     *
     * @param node the preferences node to synchronise with.
     */
    protected abstract void setPreferencesNode(Preferences node);
}
