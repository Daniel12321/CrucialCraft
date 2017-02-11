package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.PermissionsUtils;
import me.mrdaniel.crucialcraft.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config extends CCObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode config;

	public Config(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
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
		if (node == null) { this.config = this.getEmptyNode(); this.save(); }
		else { this.config = node; }
	}

	public void save() {
		try { this.loader.save(this.config); }
		catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to save config file: {}", exc); }
	}

	@Nonnull
	private CommentedConfigurationNode getEmptyNode() {
		CommentedConfigurationNode node = this.loader.createEmptyNode(ConfigurationOptions.defaults());

		node.getNode("teleport", "delay", "enabled").setValue(true);
		node.getNode("teleport", "delay", "seconds").setValue(3);

		node.getNode("homes").setComment("Here you can set the maximum amount of homes a player in a certain group can have.");
		node.getNode("homes", "default").setValue(1);
		node.getNode("homes", "moderator").setValue(3);
		node.getNode("homes", "admin").setValue(5);

		node.getNode("messages").setComment("Some messages allow the use of variables. If a message does, it will be shown above the setting.\nThe plugin will replace these variables with the correct number or text.");
		node.getNode("messages", "login").setComment("Allowed Variables: %player");
		node.getNode("messages", "login").setValue("&e%player joined the game.");
		node.getNode("messages", "logout").setComment("Allowed Variables: %player");
		node.getNode("messages", "logout").setValue("&e%player left the game.");

		node.getNode("messages", "broadcast").setComment("Allowed Variables: %message");
		node.getNode("messages", "broadcast").setValue("&6[&bBroadcast&6]&c %message");

		node.getNode("messages", "motd").setComment("Allowed Variables: %player, %online, %maxplayers");
		node.getNode("messages", "motd").setValue(Lists.newArrayList("&6Welcome &c%player &6to the server.", "&6There are currently &c%online &6/ &c%maxplayers &6players online.", "&6Do &c/rules &6to read a list of rules."));

		node.getNode("messages", "rules").setValue(Lists.newArrayList("&c1: Do not grief.", "&c2: Do not swear.", "&c3: Respect everyone."));
		node.getNode("messages", "whitelist").setValue("&cThis server is currently whitelisted.");

		node.getNode("messages", "banned").setComment(" Allowed Variables: %reason, %banner");
		node.getNode("messages", "banned", "header").setValue("&4You were banned from this server by %banner");
		node.getNode("messages", "banned", "body").setValue("&cYou will not be automatically unbanned.");
		node.getNode("messages", "banned", "footer").setValue("&cReason: %reason");

		node.getNode("messages", "tempbanned").setComment("Allowed Variables: %reason, %banner %duration");
		node.getNode("messages", "tempbanned", "header").setValue("&4You were banned from this server by %banner!");
		node.getNode("messages", "tempbanned", "body").setValue("&cYou will be unbanned in %6%duration&c.");
		node.getNode("messages", "tempbanned", "footer").setValue("&cReason: %reason");

		node.getNode("messages", "firstjoin").setComment("Allowed Variables: %player");
		node.getNode("messages", "firstjoin").setValue("&d%player joined the server for the first time!");
		return node;
	}

	public boolean isTeleportDelay() { return this.config.getNode("teleport", "delay", "enabled").getBoolean(); }
	public int getTeleportDelay() { return this.config.getNode("teleport", "delay", "seconds").getInt(); }

	@Nonnull public Text getBroadcastMessage(@Nonnull final String message) { return TextUtils.toText(this.config.getNode("messages", "broadcast").getString().replace("%message", message) + " "); }
	@Nonnull public Text getLoginMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "login").getString().replace("%player", playername)); }
	@Nonnull public Text getLogoutMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "logout").getString().replace("%player", playername)); }
	@Nonnull public List<Text> getMotd(@Nonnull final String playername, final int playersonline, final int maxplayers) { return this.config.getNode("messages", "motd").getList(obj -> TextUtils.toText(((String) obj).replace("%player", playername).replace("%online", String.valueOf(playersonline)).replace("%maxplayers", String.valueOf(maxplayers)))); }
	@Nonnull public List<Text> getRules() { return this.config.getNode("messages", "rules").getList(obj -> TextUtils.toText((String) obj)); }
	@Nonnull public Text getWhitelistMessage() { return TextUtils.toText(this.config.getNode("messages", "whitelist").getString()); }
	@Nonnull public Text getFirstJoinMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "firstjoin").getString().replace("%player", playername)); }
	@Nonnull public Text[] getBanMessage(@Nonnull final Text reason, @Nonnull final Text banner) { return new Text[]{
			TextUtils.toText(this.config.getNode("messages", "banned", "header").getString().replace("%reason", TextUtils.toString(reason).replace("%banner", TextUtils.toString(banner)))),
			TextUtils.toText(this.config.getNode("messages", "banned", "body").getString().replace("%reason", TextUtils.toString(reason).replace("%banner", TextUtils.toString(banner)))),
			TextUtils.toText(this.config.getNode("messages", "banned", "footer").getString().replace("%reason", TextUtils.toString(reason).replace("%banner", TextUtils.toString(banner)))),
	}; }
	@Nonnull public Text[] getTempBanMessage(@Nonnull final Text reason, @Nonnull final Text banner, @Nonnull final String duration) { return new Text[]{
			TextUtils.toText(this.config.getNode("messages", "tempbanned", "header").getString().replace("%banner", TextUtils.toString(banner)).replace("%reason", TextUtils.toString(reason).replace("%duration", duration))),
			TextUtils.toText(this.config.getNode("messages", "tempbanned", "body").getString().replace("%banner", TextUtils.toString(banner)).replace("%reason", TextUtils.toString(reason).replace("%duration", duration))),
			TextUtils.toText(this.config.getNode("messages", "tempbanned", "footer").getString().replace("%banner", TextUtils.toString(banner)).replace("%reason", TextUtils.toString(reason).replace("%duration", duration)))
	}; }
	@Nonnull
	public int getMaxHomes(@Nonnull final Player p) {
		List<String> groups = PermissionsUtils.getGroups(p);
		List<String> configgroups = this.config.getNode("homes").getChildrenMap().keySet().stream().map(obj -> (String)obj).collect(Collectors.toList());

		int max = 0;
		for (String group : groups) {
			if (configgroups.contains(group.toLowerCase())) {
				int i = this.config.getNode("homes", group.toLowerCase()).getInt();
				if (i > max) { max = i; }
			}
		}
		return max;
	}
}