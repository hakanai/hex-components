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

package org.trypticon.hex.interpreters;

import java.util.Map;
import java.util.List;

/**
 * Holds information about an interpreter.
 *
 * @author trejkaz
 */
public interface InterpreterInfo {

    /**
     * Gets the name the user would see in the application.
     *
     * XXX: Should be localised.
     *
     * @return the human-readable name.
     */
    String getHumanName();

    /**
     * Gets the list of options supported by the interpreter.
     * If there are no options this should return an empty list.
     *
     * @return the list of supported options.
     */
    List<Option> getOptions();

    /**
     * Creates a new interpreter with the provided options.
     *
     * @param options the options.
     * @return the interpreter.
     */
    Interpreter create(Map<String, Object> options);

    /**
     * Class describing options which can be passed to the interpreter.
     */
    public static class Option {
        private String name;
        private Class<?> type;
        private boolean required;

        public Option(String name, Class<?> type, boolean required) {
            this.name = name;
            this.type = type;
            this.required = required;
        }

        public String getName() {
            return name;
        }

        public Class<?> getType() {
            return type;
        }

        public boolean isRequired() {
            return required;
        }
    }
}
