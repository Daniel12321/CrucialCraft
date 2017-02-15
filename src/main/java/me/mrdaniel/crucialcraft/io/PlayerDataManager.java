package me.mrdaniel.crucialcraft.io;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

public interface PlayerDataManager {

	public PlayerFile get(@Nonnull final UUID uuid);

	public Optional<PlayerFile> getOffline(@Nonnull final UUID uuid);

	public Optional<PlayerFile> getOffline(@Nonnull final String name);

	public boolean load(@Nonnull final UUID uuid);

	public void unload(@Nonnull final UUID uuid);
}