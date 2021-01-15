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

package org.trypticon.hex.binary;

import org.junit.jupiter.api.AfterEach;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tests for {@link FileChannelBinary}.
 *
 * @author trejkaz
 */
public class FileChannelBinaryTest extends AbstractBinaryTest {
    Path tempFile;

    @Override
    protected Binary createBinary(byte[] sampleData) throws Exception {
        tempFile = Files.createTempFile("FileChannelBinaryTest", ".dat");
        Files.write(tempFile, sampleData);
        return new FileChannelBinary(tempFile);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (tempFile != null) {
            Files.delete(tempFile);
        }
    }
}
