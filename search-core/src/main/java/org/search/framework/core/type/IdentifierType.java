package org.search.framework.core.type;

/**
 * Contract that will allow Types with id to have generic implementation.
 * 
 * @author sabir
 *
 */
public interface IdentifierType<T> {

    T getId();

}
