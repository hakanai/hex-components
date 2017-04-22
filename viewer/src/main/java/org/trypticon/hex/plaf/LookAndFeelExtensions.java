/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

package org.trypticon.hex.plaf;

import org.trypticon.hex.util.swingsupport.PLAFUtils;

import javax.swing.Action;
import javax.swing.LookAndFeel;
import javax.swing.TransferHandler;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Registration of additional look and feel properties.
 *
 * @author trejkaz
 */
public class LookAndFeelExtensions {
    static {
        manipulateDefaults();
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if ("lookAndFeel".equals(event.getPropertyName())) {
                    manipulateDefaults();
                }
            }
        });
    }

    /**
     * Ensures that the extensions are initialised.
     */
    public static void ensureInitialised() {
        // Does nothing but by calling it, the static initialised is called.
    }

    /**
     * Inserts defaults into the look and feel for any properties we use which aren't already present.
     */
    private static void manipulateDefaults() {
        UIDefaults defaults = UIManager.getDefaults();

        if (defaults.get("HexViewerUI") == null) {
            defaults.put("HexViewerUI", BasicHexViewerUI.class.getName());
        }

        if (defaults.get("HexViewer.font") == null) {
            defaults.put("HexViewer.font", new FontUIResource("monospaced", Font.PLAIN, 12));
        }

        if (defaults.get("HexViewer.focusInputMap") == null) {
            if (PLAFUtils.isAqua()) {
                defaults.put("HexViewer.focusInputMap", LookAndFeel.makeInputMap(new Object[] {
                    "DOWN",             "cursorDown",
                    "PAGE_DOWN",        "cursorPageDown",
                    "alt DOWN",         "cursorPageDown",
                    "UP",               "cursorUp",
                    "PAGE_UP",          "cursorPageUp",
                    "alt UP",           "cursorPageUp",
                    "LEFT",             "cursorLeft",
                    "meta LEFT",        "cursorLineStart",
                    "RIGHT",            "cursorRight",
                    "meta RIGHT",       "cursorLineEnd",
                    "meta UP",          "cursorHome",
                    "meta DOWN",        "cursorEnd",
                    "shift DOWN",       "selectionDown",
                    "shift PAGE_DOWN",  "selectionPageDown",
                    "shift alt DOWN",   "selectionPageDown",
                    "shift UP",         "selectionUp",
                    "shift PAGE_UP",    "selectionPageUp",
                    "shift alt UP",     "selectionPageUp",
                    "shift LEFT",       "selectionLeft",
                    "shift meta LEFT",  "selectionLineStart",
                    "shift RIGHT",      "selectionRight",
                    "shift meta RIGHT", "selectionLineEnd",
                    "shift meta UP",    "selectionHome",
                    "shift meta DOWN",  "selectionEnd",
                    "meta A",           "selectAll",
                    "meta X",           TransferHandler.getCutAction().getValue(Action.NAME),
                    "meta C",           TransferHandler.getCopyAction().getValue(Action.NAME),
                    "meta V",           TransferHandler.getPasteAction().getValue(Action.NAME),
                }));
            } else {
                defaults.put("HexViewer.focusInputMap", LookAndFeel.makeInputMap(new Object[] {
                    "DOWN",             "cursorDown",
                    "PAGE_DOWN",        "cursorPageDown",
                    "UP",               "cursorUp",
                    "PAGE_UP",          "cursorPageUp",
                    "LEFT",             "cursorLeft",
                    "HOME",             "cursorLineStart",
                    "RIGHT",            "cursorRight",
                    "END",              "cursorLineEnd",
                    "ctrl HOME",        "cursorHome",
                    "ctrl END",         "cursorEnd",
                    "shift DOWN",       "selectionDown",
                    "shift PAGE_DOWN",  "selectionPageDown",
                    "shift UP",         "selectionUp",
                    "shift PAGE_UP",    "selectionPageUp",
                    "shift LEFT",       "selectionLeft",
                    "shift HOME",       "selectionLineStart",
                    "shift RIGHT",      "selectionRight",
                    "shift END",        "selectionLineEnd",
                    "shift ctrl HOME",  "selectionHome",
                    "shift ctrl END",   "selectionEnd",
                    "ctrl A",           "selectAll",
                    "ctrl X",           TransferHandler.getCutAction().getValue(Action.NAME),
                    "ctrl C",           TransferHandler.getCopyAction().getValue(Action.NAME),
                    "ctrl V",           TransferHandler.getPasteAction().getValue(Action.NAME),
                }));
            }
        }
    }
}
