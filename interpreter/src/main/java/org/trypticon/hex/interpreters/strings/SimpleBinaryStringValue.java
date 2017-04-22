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

package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.interpreters.AbstractValue;
import org.trypticon.hex.util.Format;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Simple implementation of a binary string value.
 *
 * @author trejkaz
 */
public class SimpleBinaryStringValue extends AbstractValue implements BinaryStringValue {
    private final Binary binary;

    public SimpleBinaryStringValue(Binary binary) {
        this.binary = binary;
    }

    @Override
    public long length() {
        return binary.length();
    }

    @Override
    public String toLocalisedString(Format style, Locale locale) {
        return new StringInterpreter(StandardCharsets.UTF_8)
                .interpret(binary, 0, binary.length())
                .toLocalisedString(style, locale);
    }
}
