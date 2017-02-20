package me.mrdaniel.crucialcraft;

import javax.annotation.Nonnull;

public abstract class CCModule extends CCObject {

	public CCModule(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	public abstract void registerListeners();
	public abstract void registerCommands();
}