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

import org.trypticon.hex.util.Name;
import org.trypticon.hex.util.NameStyle;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * A list cell renderer which can use an object's {@link Name}.
 *
 * @author trejkaz
 */
//XXX: I want this to be DelegatingListCellRenderer<Name> but if you do that, the super call is invalid.
public class NameListCellRenderer extends DelegatingListCellRenderer<Object> {
    protected NameListCellRenderer(ListCellRenderer<Object> delegate) {
        super(delegate);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Name) {
            value = ((Name) value).getLocalisedName(NameStyle.LONG);
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
