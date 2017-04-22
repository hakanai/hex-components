/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

package org.trypticon.hex.anno;

/**
 * Class used as keys to look up attributes.
 *
 * @author trejkaz
 */
public final class Attribute<V> {
    private final String name;

    private Attribute(String name) {
        this.name = name;
    }

    /**
     * Creates an attribute with the given name.
     * This is a factory method to aid with the use of generics.
     *
     * @param name the name of the attribute. Names should start with a
     *             package-like namespace to aid in preventing collisions.
     * @param <V> the type of the attribute value.
     * @return the attribute.
     * @see CommonAttributes
     */
    public static <V> Attribute<V> named(String name) {
        return new Attribute<>(name);
    }
}
