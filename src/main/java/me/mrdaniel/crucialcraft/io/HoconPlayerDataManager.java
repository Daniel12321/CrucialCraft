package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import org.spongepowered.api.profile.GameProfile;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class HoconPlayerDataManager extends CCObject implements PlayerDataManager {

	private final ConcurrentHashMap<UUID, HoconPlayerFile> players;
	private final Path path;

	public HoconPlayerDataManager(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
		super(cc);

		this.players = new ConcurrentHashMap<UUID, HoconPlayerFile>();
		this.path = path;

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { cc.getLogger().error("Failed to create main playerdata directory: {}", exc); }
		}

		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> this.load(p.getUniqueId()));
	}

	@Nonnull
	public synchronized PlayerFile get(@Nonnull final UUID uuid) {
		return this.players.get(uuid);
	}

	@Nonnull
	public Optional<PlayerFile> getOffline(@Nonnull final UUID uuid) {
		Path path = this.path.resolve(uuid.toString() + ".conf");
		if (!Files.exists(path)) { return Optional.empty(); }

		return Optional.of(new HoconPlayerFile(super.getCrucialCraft(), path));
	}

	@Nonnull
	public Optional<PlayerFile> getOffline(@Nonnull final String name) {
		Optional<GameProfile> gp = super.getCrucialCraft().getGame().getServer().getGameProfileManager().getCache().getByName(name);
		if (!gp.isPresent()) { return Optional.empty(); }

		return this.getOffline(gp.get().getUniqueId());
	}

	public synchronized boolean load(@Nonnull final UUID uuid) {
		Path path = this.path.resolve(uuid.toString() + ".conf");
		boolean firstjoin = false;

		if (!Files.exists(path)) { firstjoin = true; }
		this.players.put(uuid, new HoconPlayerFile(super.getCrucialCraft(), path));

		return firstjoin;
	}

	public synchronized void unload(@Nonnull final UUID uuid) {
		this.players.remove(uuid);
	}

	@Nonnull
	public CommentedConfigurationNode addDefaults(@Nonnull final CommentedConfigurationNode node) {
		node.getNode("last_login").setValue(System.currentTimeMillis());
		node.getNode("last_logout").setValue(System.currentTimeMillis());

		return node;
	}
}