package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class DataFile extends CCObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode config;

	private Optional<Teleport> spawn;
	private Optional<Teleport> newbiespawn;
	private Map<String, Teleport> warps;
	private Map<UUID, Long> tempbans;
	private Map<String, Teleport> jails;

	public DataFile(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
		super(cc);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		CommentedConfigurationNode node = null;
		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to create config file: {}", exc); }
		}
		else {
			try { node = loader.load(); }
			catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to load config file: {}", exc); }
		}
		if (node == null) { this.config = this.loader.createEmptyNode(ConfigurationOptions.defaults()); this.save(); }
		else { this.config = node; }

		this.spawn = Teleport.deserialize(this.config.getNode("spawn").getString());
		this.newbiespawn = Teleport.deserialize(this.config.getNode("newbiespawn").getString());

		this.warps = Maps.newHashMap();
		this.config.getNode("warps").getChildrenMap().forEach((key, value) -> Teleport.deserialize(value.getString()).ifPresent(teleport -> this.warps.put((String) key, teleport)));

		this.tempbans = Maps.newHashMap();
		this.config.getNode("tempbans").getChildrenMap().forEach((key, value) -> this.tempbans.put(UUID.fromString((String)key), value.getLong()));

		this.jails = Maps.newHashMap();
		this.config.getNode("jails").getChildrenMap().forEach((key, value) -> Teleport.deserialize((String)key).ifPresent(teleport -> this.jails.put((String)key, teleport)));
	}

	private void save() {
		try { this.loader.save(this.config); }
		catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to save data file: {}", exc); }
	}

	// Spawn
	@Nonnull public Optional<Teleport> getSpawn() { return this.spawn; }
	public void setSpawn(@Nullable final Teleport teleport) {
		this.spawn = Optional.ofNullable(teleport);
		if (teleport != null) { this.config.getNode("spawn").setValue(teleport.serialize()); }
		else { this.config.removeChild("spawn"); }
		this.save();
	}

	// Newbie Spawn
	@Nonnull public Optional<Teleport> getNewbieSpawn() { return this.newbiespawn; }
	public void setNewbieSpawn(@Nullable final Teleport teleport) {
		this.newbiespawn = Optional.ofNullable(teleport);
		if (teleport != null) { this.config.getNode("newbiespawn").setValue(teleport.serialize()); }
		else { this.config.removeChild("newbiespawn"); }
		this.save();
	}

	// Warps
	@Nonnull public Set<String> getWarps() { return this.warps.keySet(); }
	@Nonnull public Optional<Teleport> getWarp(@Nonnull final String name) { return Optional.ofNullable(this.warps.get(name)); }
	public void setWarp(@Nonnull final String name, @Nullable final Teleport teleport) {
		if (teleport != null) { this.warps.put(name, teleport); this.config.getNode("warps", name).setValue(teleport.serialize()); }
		else { this.warps.remove(name); this.config.getNode("warps").removeChild(name); }
		this.save();
	}

	// Temp Bans
	@Nonnull public Optional<Long> getTempBan(@Nonnull final UUID uuid) {
		if (this.config.getNode("tempbans", uuid.toString()).isVirtual()) { return Optional.empty(); }
		if (this.config.getNode("tempbans", uuid.toString()).getLong() < System.currentTimeMillis()) { this.config.getNode("tempbans").removeChild(uuid.toString()); this.save(); return Optional.empty(); }
		return Optional.of(this.config.getNode("tempbans", uuid.toString()).getLong());
	}
	public void setTempBan(@Nonnull final UUID uuid, final long millis) {
		if (millis > 0) { this.tempbans.put(uuid, millis); this.config.getNode("tempbans", uuid.toString()).setValue(System.currentTimeMillis() + millis); }
		else { this.tempbans.remove(uuid); this.config.getNode("tempbans").removeChild(uuid.toString()); }
		this.save();
	}

	// Jails
	@Nonnull public Set<String> getJails() { return this.jails.keySet(); }
	@Nonnull public Optional<Teleport> getJail(@Nonnull final String name) { return Optional.ofNullable(this.jails.get(name)); }
	public void setJail(@Nonnull final String name, @Nullable final Teleport teleport) {
		if (teleport != null) { this.jails.put(name, teleport); this.config.getNode("jails", name).setValue(teleport.serialize()); }
		else { this.jails.remove(name); this.config.getNode("jails").removeChild(name); }
		this.save();
	}
}