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

package org.trypticon.hex.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

/**
 * Logger utility methods.
 *
 * @author trejkaz
 */
public class LoggerUtils {
    private static final StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private LoggerUtils() {
    }

    /**
     * Gets a logger appropriate for whichever class was the caller.
     *
     * @return the logger.
     */
    public static Logger get() {
        return Logger.getLogger(stackWalker.getCallerClass().getName());
    }
}
