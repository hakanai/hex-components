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

package org.trypticon.hex.accessory;

import org.trypticon.hex.interpreters.Value;
import org.trypticon.hex.util.Format;

import javax.swing.JFormattedTextField;
import java.text.ParseException;

/**
 * Formatter factory to support the types we know how to interpret.
 *
 * @author trejkaz
 */
// Swing's own guidelines say not to use serialisation.
@SuppressWarnings("serial")
class ValueFormatterFactory extends JFormattedTextField.AbstractFormatterFactory {
    @Override
    public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField textField) {
        return new ValueFormatter();
    }

    private static class ValueFormatter extends JFormattedTextField.AbstractFormatter {
        @Override
        public Object stringToValue(String text) throws ParseException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value == null) {
                return "";
            } else if (value instanceof Value) {
                return ((Value) value).toLocalisedString(Format.LONG);
            } else if (value instanceof String) {
                return (String) value;
            }
            throw new ParseException("Value is not one of the expected types: " + value, 0);
        }
    }
}
