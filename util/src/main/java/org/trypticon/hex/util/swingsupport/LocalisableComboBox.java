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

package org.trypticon.hex.util.swingsupport;

import org.trypticon.hex.util.Format;
import org.trypticon.hex.util.Localisable;

import javax.swing.ListCellRenderer;

/**
 * A combo box which renders the elements using {@link org.trypticon.hex.util.Localisable}
 *
 * @author trejkaz
 */
public class LocalisableComboBox<E extends Localisable> extends DelegateRenderingComboBox<E> {
    private final Format format;

    public LocalisableComboBox(Format format) {
        super();
        this.format = format;
        updateUI();
    }

    public LocalisableComboBox(Format format, E[] items) {
        super(items);
        this.format = format;
        updateUI();
    }

    @Override
    protected DelegatingListCellRenderer<? super E> createRenderer(ListCellRenderer<Object> delegate) {
        Format format = this.format;
        if (format == null) { // happens during call from superclass constructor
            format = Format.LONG;
        }
        return new LocalisableListCellRenderer(delegate, format);
    }
}
