package org.trypticon.hex.plaf;

import org.trypticon.hex.util.swingsupport.PLAFUtils;

import javax.swing.Action;
import javax.swing.LookAndFeel;
import javax.swing.TransferHandler;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
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

        if (defaults.get("HexViewer.focusInputMap") == null) {
            if (PLAFUtils.isAqua()) {
                defaults.put("HexViewer.focusInputMap", LookAndFeel.makeInputMap(new Object[] {
                    "DOWN",             "cursorDown",
                    "PAGE_DOWN",        "cursorPageDown",
                    "alt DOWN",         "cursorPageDown",
                    "UP",               "cursorUp",
                    "PAGE_UP",          "cursorPageUp",
                    "alt PAGE_UP",      "cursorPageUp",
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
