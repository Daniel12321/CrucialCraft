package me.mrdaniel.crucialcraft;

import javax.annotation.Nonnull;

public abstract class CCObject {

	private final CrucialCraft cc;

	public CCObject(@Nonnull final CrucialCraft cc) {
		this.cc = cc;
	}

	@Nonnull
	protected CrucialCraft getCrucialCraft() {
		return this.cc;
	}
}