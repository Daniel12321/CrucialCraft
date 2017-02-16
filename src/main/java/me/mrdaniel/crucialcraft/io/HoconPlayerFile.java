package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.text.Text;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.TextUtils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class HoconPlayerFile extends CCObject implements PlayerFile {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode config;

	public HoconPlayerFile(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
		super(cc);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		CommentedConfigurationNode node;
		try {
			if (!Files.exists(path)) { Files.createFile(path); node = this.loader.createEmptyNode(); }
			else { node = this.loader.load(); }
		}
		catch (final IOException exc) {
			super.getCrucialCraft().getLogger().error("Failed to load playerdata file: {}", exc);
			node = this.loader.createEmptyNode();
		}
		this.config = node;
	}

	public void save() {
		try { this.loader.save(this.config); }
		catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to save config file: {}", exc); }
	}

	@Nonnull
	private CommentedConfigurationNode getNewNode() {
		return ((HoconPlayerDataManager)super.getCrucialCraft().getPlayerData()).addDefaults(this.loader.createEmptyNode());
	}

	@Nonnull public List<String> getHomes() { return this.config.getNode("homes").getChildrenMap().keySet().stream().map(obj -> (String)obj).collect(Collectors.toList()); }
	@Nonnull public Optional<Teleport> getHome(@Nonnull final String name) { return Teleport.deserialize(this.config.getNode("homes", name).getString()); }
	public void setHome(@Nonnull final String name, @Nullable final Teleport tp) {
		if (tp == null) { this.config.getNode("homes").removeChild(name); }
		else { this.config.getNode("homes", name).setValue(tp.serialize()); }
		this.save(); 
	}

	@Nonnull public Optional<Text> getNick() { return Optional.ofNullable(this.config.getNode("nick").getString()).map(TextUtils::toText); }
	public void setNick(@Nullable final Text nick) {
		if (nick == null) { this.config.removeChild("nick"); }
		else { this.config.getNode("nick").setValue(TextUtils.toString(nick)); }
		this.save();
	}

	@Nonnull public Optional<Teleport> getLastLocation() { return Teleport.deserialize(this.config.getNode("last_location").getString()); }
	public void setLastLocation(@Nonnull final Teleport tp) { this.config.getNode("last_location").setValue(tp.serialize()); this.save(); }

	@Nonnull public Optional<String> getLastMessager() { return Optional.ofNullable(this.config.getNode("last_messager").getString()); }
	public void setLastMessager(@Nonnull final String messager) { this.config.getNode("last_messager").setValue(messager); this.save(); }

	public boolean isJailed() { return this.config.getNode("jailed").getBoolean(); }
	public void setJailed(final boolean jailed) { this.config.getNode("jailed").setValue(jailed);this.save(); }

	public boolean isMuted() { return this.config.getNode("muted").getBoolean(); }
	public void setMuted(final boolean muted) { this.config.getNode("muted").setValue(muted); this.save(); }

	public long getLastLogin() { return this.config.getNode("last_login").getLong(); }
	public void setLastLogin(final long login) { this.config.getNode("last_login").setValue(login); this.save(); }

	public long getLastLogout() { return this.config.getNode("last_logout").getLong(); }
	public void setLastLogout(final long logout) { this.config.getNode("last_logout").setValue(logout); this.save(); }

	public long getPlaytime() { return this.config.getNode("playtime").getLong(); }
	public void setPlaytime(final long playtime) { this.config.getNode("playtime").setValue(playtime); this.save(); }
	public long getCurrentPlaytime() { return (this.getLastLogin() > this.getLastLogout()) ? this.getPlaytime() + System.currentTimeMillis() - this.getLastLogin() : this.getPlaytime(); }

	public long getLastKitUse(@Nonnull final String name) { return this.config.getNode("kits", name).getLong(); }
	public void setLastKitUse(@Nonnull final String name, final long time) { this.config.getNode("kits", name).setValue(time); this.save(); }

	@Nonnull public List<String> getMail() { return this.config.getNode("mail").getList(obj -> (String)obj); }
	public void addMail(@Nonnull final String sender, @Nonnull final String message) { List<String> mail = this.getMail(); mail.add(sender + ": " + message); this.config.getNode("mail").setValue(mail); this.save(); }
	public void clearMail() { this.config.removeChild("mail"); this.save(); }
}