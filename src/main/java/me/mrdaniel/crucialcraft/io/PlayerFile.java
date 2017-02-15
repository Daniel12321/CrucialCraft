package me.mrdaniel.crucialcraft.io;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.mrdaniel.crucialcraft.teleport.Teleport;

public interface PlayerFile {

	@Nonnull public List<String> getHomes();
	@Nonnull public Optional<Teleport> getHome(@Nonnull String name);
	public void setHome(@Nonnull String name, @Nullable Teleport tp);

	@Nonnull public Optional<String> getNick();
	public void setNick(@Nullable String nick);

	@Nonnull public Optional<Teleport> getLastLocation();
	public void setLastLocation(@Nullable Teleport tp);

	@Nonnull public Optional<String> getLastMessager();
	public void setLastMessager(@Nullable String messager);

	public boolean isJailed();
	public void setJailed(boolean jailed);

	public boolean isMuted();
	public void setMuted(boolean muted);

	public long getLastLogin();
	public void setLastLogin(long login);

	public long getLastLogout();
	public void setLastLogout(long logout);

	public long getPlaytime();
	public void setPlaytime(long playtime);
	public long getCurrentPlaytime();

	public long getLastKitUse(@Nonnull String name);
	public void setLastKitUse(@Nonnull String name, long time);
}