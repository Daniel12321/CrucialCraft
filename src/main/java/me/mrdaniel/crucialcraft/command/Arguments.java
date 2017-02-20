package me.mrdaniel.crucialcraft.command;

import java.util.HashMap;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

public class Arguments {

	private final HashMap<String, Object> args;

	protected Arguments() {
		this.args = Maps.newHashMap();
	}

	protected void add(@Nonnull final String key, @Nonnull final Object value) {
		if (value != null) { this.args.put(key, value); }
	}

	public boolean has(@Nonnull final String key) {
		return this.args.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(@Nonnull final String key) {
		return (T) this.args.get(key);
	}
}