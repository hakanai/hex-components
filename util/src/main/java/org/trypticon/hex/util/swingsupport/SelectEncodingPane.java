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

import org.jdesktop.swingx.renderer.StringValue;
import org.trypticon.hex.util.Predicate;
import org.trypticon.hex.util.Strings;

import java.awt.Component;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Pane to select a character encoding.
 *
 * @author trejkaz
 */
// Swing's own guidelines say not to use serialisation.
@SuppressWarnings("serial")
class SelectEncodingPane extends SelectObjectPane<Charset> {
    @Override
    protected List<Charset> createList() {
        return new ArrayList<>(Charset.availableCharsets().values());
    }

    @Override
    protected StringValue createDisplayConverter() {
        return new StringValue() {
            @Override
            public String getString(Object element) {
                return ((Charset) element).name();
            }
        };
    }

    @Override
    protected Predicate<Charset> createFilterPredicate(String filterText) {
        final String[] textFragments = Strings.splitOnWhitespace(filterText);
        return new Predicate<Charset>() {
            @Override
            public boolean test(Charset charset) {
                for (String fragment : textFragments) {
                    if (Strings.containsIgnoreCase(charset.name(), fragment)) {
                        continue;
                    }

                    boolean found = false;
                    for (String alias : charset.aliases()) {
                        if (Strings.containsIgnoreCase(alias, fragment)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        continue;
                    }

                    return false;
                }
                return true;
            }
        };
    }

    /**
     * Shows the encoding selection pane in a dialog.
     *
     * @param parentComponent the parent component.
     * @return the chosen encoding.
     */
    public Charset showDialog(Component parentComponent) {
        ResourceBundle bundle = ResourceBundle.getBundle("org/trypticon/hex/Bundle");
        return showDialog(parentComponent,
                          bundle.getString("SelectEncoding.title"),
                          bundle.getString("SelectEncoding.okButton"));
    }
}
