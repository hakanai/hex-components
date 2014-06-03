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

package org.trypticon.hex.util;

import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link URLUtils}.
 *
 * @author trejkaz
 */
public class URLUtilsTest {

    @Test
    public void testToPath() throws Exception {
        Path file = Paths.get(System.getProperty("user.home"));
        assertEquals("Round trip gives the wrong result", file, URLUtils.toPath(file.toUri().toURL()));
    }

    @Test
    public void testToFileForInvalidURL() throws Exception {
        assertEquals("Wrong result for invalid URL case", Paths.get("/path/with spaces"),
                     URLUtils.toPath(new URL("file:/path/with spaces")));
    }
}
