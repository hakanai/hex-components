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

package org.trypticon.hex.util.swingsupport;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * Look and Feel utilities.
 *
 * @author trejkaz
 */
public class PLAFUtils {
    private PLAFUtils() {
    }

    /**
     * Sets the given components to appear in the smaller size. This affects some look and feels
     * (particularly Aqua and Nimbus.)
     *
     * @param components the components to make small.
     */
    public static void makeSmall(JComponent... components) {
        for (JComponent component : components) {
            component.putClientProperty("JComponent.sizeVariant", "small");
        }
    }

    /**
     * Sets the given combo boxes to appear in the square style. Actually, on modern versions of Mac OS X,
     * combo boxes are this style by default - but it looks like setting this option adjusts the borders
     * of the combo box to line up better with other components like text fields.
     *
     * @param comboBoxes the combo boxes to make square.
     */
    public static void makeSquare(JComboBox<?>... comboBoxes) {
        for (JComboBox<?> comboBox : comboBoxes) {
            comboBox.putClientProperty("JComboBox.isSquare", true);
        }
    }

    /**
     * Tests if the look and feel is Aqua or a derivative (e.g. Quaqua.)
     *
     * @return {@code true} if using Aqua, otherwise {@code false}.
     */
    public static boolean isAqua() {
        String lafClassName = UIManager.getLookAndFeel().getClass().getName();
        return "ch.randelshofer.quaqua.QuaquaLookAndFeel".equals(lafClassName) ||
                "apple.laf.AquaLookAndFeel".equals(lafClassName) ||
                "com.apple.laf.AquaLookAndFeel".equals(lafClassName);
    }

    /**
     * Tests if the look and feel is Quaqua, which we use on Mac to get better look and feel for Mac.
     *
     * @return {@code true} if using Quaqua, otherwise {@code false}.
     */
    public static boolean isQuaqua() {
        return "ch.randelshofer.quaqua.QuaquaLookAndFeel".equals(UIManager.getLookAndFeel().getClass().getName());
    }
}
