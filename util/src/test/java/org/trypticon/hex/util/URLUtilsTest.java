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

import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link URLUtils}.
 *
 * @author trejkaz
 */
public class URLUtilsTest {

    @Test
    public void testToPath() throws Exception {
        Path file = Paths.get(System.getProperty("user.home"));
        assertThat(URLUtils.toPath(file.toUri().toURL()), is(file));
    }

    @Test
    public void testToFileForInvalidURL() throws Exception {
        assertThat(URLUtils.toPath(new URL("file:/path/with spaces")),
                   is(Paths.get("/path/with spaces")));
    }
}
