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

import org.trypticon.hex.util.Format;
import org.trypticon.hex.util.Localisable;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * A list cell renderer which can use an object's {@link org.trypticon.hex.util.Localisable}.
 *
 * @author trejkaz
 */
//XXX: I want this to be DelegatingListCellRenderer<Localisable> but if you do that, the super call is invalid.
public class LocalisableListCellRenderer extends DelegatingListCellRenderer<Object> {
    private final Format format;

    protected LocalisableListCellRenderer(ListCellRenderer<Object> delegate, Format format) {
        super(delegate);
        this.format = format;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Localisable) {
            value = ((Localisable) value).toLocalisedString(format);
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
