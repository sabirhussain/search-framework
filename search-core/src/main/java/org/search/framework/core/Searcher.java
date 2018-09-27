package org.search.framework.core;

/**
 * Contract defining search functionality.
 * 
 * @author sabir
 *
 * @param <Q> {@link Query} type.
 * @param <T> search result type.
 */
public interface Searcher<Q, T> {
    T search(Query<Q> query);
}
