package com.github.czyzby.lml.util.gdx.collection;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.czyzby.lml.util.gdx.collection.disposable.DisposableObjectMap;
import com.github.czyzby.lml.util.gdx.collection.immutable.ImmutableObjectMap;

/** Simple ObjectMap utilities, somewhat inspired by Guava.
 *
 * @author MJ */
public class GdxMaps {
	private GdxMaps() {
	}

	/** @return an empty, new object map. */
	public static <Key, Value> ObjectMap<Key, Value> newObjectMap() {
		return new ObjectMap<Key, Value>();
	}

	/** @param keyAndValues pairs of keys and values. Each value has to be proceeded by a key.
	 * @return a new object map with the given values. Not fail-fast - be careful when passing arguments, or
	 *         it might result in unexpected map values. */
	@SuppressWarnings("unchecked")
	public static <Key, Value> ObjectMap<Key, Value> newObjectMap(final Object... keyAndValues) {
		if (keyAndValues.length % 2 != 0) {
			throw new IllegalArgumentException("Total number of keys and values has to be even.");
		}
		final ObjectMap<Key, Value> map = new ObjectMap<Key, Value>();
		for (int pairIndex = 0; pairIndex < keyAndValues.length; pairIndex++) {
			map.put((Key) keyAndValues[pairIndex], (Value) keyAndValues[++pairIndex]);
		}
		return map;
	}

	/** @return a new disposable object map with the passed values. */
	public static <Key, Value extends Disposable> DisposableObjectMap<Key, Value> toDisposable(
			final ObjectMap<? extends Key, ? extends Value> map) {
		return new DisposableObjectMap<Key, Value>(map);
	}

	/** @return a new immutable map with the passed values. */
	public static <Key, Value> ImmutableObjectMap<Key, Value> toImmutable(
			final ObjectMap<? extends Key, ? extends Value> map) {
		return new ImmutableObjectMap<Key, Value>(map);
	}

	/** @return true if map is null or has no elements. */
	public static boolean isEmpty(final ObjectMap<?, ?> map) {
		return map == null || map.size == 0;
	}

	/** @return true if map is not null and has at least one element. */
	public static boolean isNotEmpty(final ObjectMap<?, ?> map) {
		return map != null && map.size > 0;
	}
}