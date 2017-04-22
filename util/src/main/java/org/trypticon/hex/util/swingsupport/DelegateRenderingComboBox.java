/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
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
import javax.swing.ListCellRenderer;

/**
 * A combo box utilising a delegate renderer.
 *
 * @author trejkaz
 */
public abstract class DelegateRenderingComboBox<E> extends JComboBox<E> {
    public DelegateRenderingComboBox() {
        super();
    }

    public DelegateRenderingComboBox(E[] items) {
        super(items);
    }

    /**
     * Implemented by subclasses to return an appropriate renderer.
     *
     * @param delegate the delegate renderer.
     * @return the renderer.
     */
    protected abstract DelegatingListCellRenderer<? super E> createRenderer(ListCellRenderer<Object> delegate);

    @Override
    public void updateUI() {
        setRenderer(null); // let the UI insert its own

        super.updateUI();

        @SuppressWarnings("unchecked") // it must be this type to support any model by default.
        ListCellRenderer<Object> defaultRenderer = (ListCellRenderer<Object>) getRenderer();
        setRenderer(createRenderer(defaultRenderer));
    }
}
