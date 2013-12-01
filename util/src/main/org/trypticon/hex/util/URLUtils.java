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

package org.trypticon.hex.util;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Utilities for dealing with URLs.
 *
 * @author trejkaz
 */
public class URLUtils {

    private URLUtils() {
    }

    /**
     * Converts a {@link URL} into a {@link Path}.
     *
     * @param location the URL.
     * @return the path.
     */
    public static Path toPath(URL location) {
        try {
            return Paths.get(location.toURI());
        } catch (URISyntaxException e) {
            // Tolerance for bad URLs, but should not happen.
            LoggerUtils.get().log(Level.WARNING, "Illegal URI syntax in URL somehow: " + location, e);
            return Paths.get(location.getPath());
        }
    }

    /**
     * Decodes a URL-encoded string.
     *
     * @param urlEncoded the URL-encoded string.
     * @return the decoded string.
     */
    public static String decode(String urlEncoded) {
        try {
            return URLDecoder.decode(urlEncoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.get().log(Level.WARNING, "UTF-8 missing from the JRE somehow, decoding URL: " + urlEncoded, e);
            return urlEncoded;
        }
    }
}
