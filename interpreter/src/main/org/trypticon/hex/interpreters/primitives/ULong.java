/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

package org.trypticon.hex.interpreters.primitives;

import java.math.BigInteger;

import org.trypticon.hex.interpreters.Value;

/**
 * An unsigned int value.
 *
 * @author trejkaz
 */
public class ULong extends Number implements Value {
    private final long value;

    public ULong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public long length() {
        return 8;
    }

    public String toString() {
        BigInteger signed = BigInteger.valueOf(value);
        if (signed.signum() < 0) {
            signed = signed.add(BigInteger.ONE.shiftLeft(64));
        }
        return signed.toString();
    }
}
