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

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

/**
 * Test cases common to multiple binary tests.
 *
 * @author trejkaz
 */
public abstract class AbstractBinaryTest {
    private static final byte[] SAMPLE_DATA = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Called to create the binary to test.
     *
     * @param sampleData the sample data to put into the binary.
     * @return the binary to test. It can contain anything you want.
     * @throws Exception if an error occurs creating it.
     */
    protected abstract Binary createBinary(byte[] sampleData) throws Exception;

    @Test
    public void testReading() throws Exception {
        try (Binary binary = createBinary(SAMPLE_DATA)) {
            assertThat(binary.read(0), is(0));

            byte[] tmp = new byte[4];
            binary.read(1, tmp);
            assertThat(tmp, is(new byte[]{1, 2, 3, 4}));
            binary.read(5, tmp);
            assertThat(tmp, is(new byte[]{5, 6, 7, 8}));

            assertThat(binary.read(9), is(9));
        }
    }

    @Test
    public void testReading_OneByteAtStartBoundary() throws Exception {
        try (Binary binary = createBinary(SAMPLE_DATA)) {
            binary.read(0);

            try {
                binary.read(-1);
                fail("Expected IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {
                // Expected.
            }
        }
    }

    @Test
    public void testReading_OneByteAtEndBoundary() throws Exception {
        try (Binary binary = createBinary(SAMPLE_DATA)) {
            binary.read(binary.length() - 1);

            try {
                binary.read(binary.length());
                fail("Expected IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {
                // Expected.
            }
        }
    }

    @Test
    public void testReading_MultipleBytesAtStartBoundary() throws Exception {
        try (Binary binary = createBinary(SAMPLE_DATA)) {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            binary.read(0, buffer);

            buffer.clear();
            try {
                binary.read(-1, buffer);
                fail("Expected IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {
                // Expected.
            }
        }
    }

    @Test
    public void testReading_MultipleBytesAtEndBoundary() throws Exception {
        try (Binary binary = createBinary(SAMPLE_DATA)) {
            ByteBuffer buffer = ByteBuffer.allocate(1);
            binary.read(binary.length() - 1, buffer);

            buffer.clear();
            try {
                binary.read(binary.length(), buffer);
                fail("Expected IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {
                // Expected.
            }
        }
    }
}
