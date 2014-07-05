package org.trypticon.hex.interpreters.strings;

import org.trypticon.hex.interpreters.Value;

/**
 * A binary string value.
 *
 * @author trejkaz
 */
public interface BinaryStringValue extends Value {

    /**
     * Gets a string representation of the value.
     * Since the encoding is unknown, the results will be pretty arbitrary.
     *
     * @return the string.
     */
    String toString();
}
