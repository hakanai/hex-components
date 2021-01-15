/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
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

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * Renderer which delegates to another renderer.
 *
 * @param <E> the type of element rendered by this renderer.
 * @author trejkaz
 */
public class DelegatingListCellRenderer<E> implements ListCellRenderer<E> {
    private final ListCellRenderer<Object> delegate;

    protected DelegatingListCellRenderer(ListCellRenderer<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    // rawtypes and unchecked suppressions here are both Java's own fault
    // for not having generics on the parameter
    @SuppressWarnings("unchecked")
    public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        return delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
