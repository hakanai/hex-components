package org.trypticon.hex.util;

/**
 * A function for implementing predicates.
 *
 * @param <T> the type of object passed to the predicate.
 */
public interface Predicate<T> {

    /**
     * Tests an object against the predicate.
     *
     * @param t the object to test.
     * @return {@code true} if the predicate is satisfied, {@code false} otherwise.
     */
    boolean test(T t);
}
