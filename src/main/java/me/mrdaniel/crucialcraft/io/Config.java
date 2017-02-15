package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
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
		node.getNode("teleport", "expiry", "seconds").setValue(60);

		node.getNode("messages").setComment("Some messages allow the use of variables. If a message does, it will be shown above the setting.\nThe plugin will replace these variables with the correct number or text.");
		node.getNode("messages", "login").setComment("Allowed Variables: %player");
		node.getNode("messages", "login").setValue("&e%player joined the game.");
		node.getNode("messages", "logout").setComment("Allowed Variables: %player");
		node.getNode("messages", "logout").setValue("&e%player left the game.");
		node.getNode("messages", "firstjoin").setComment("Allowed Variables: %player");
		node.getNode("messages", "firstjoin").setValue("&d%player joined the server for the first time!");

		node.getNode("messages", "broadcast").setComment("Allowed Variables: %message");
		node.getNode("messages", "broadcast").setValue("&6[&bBroadcast&6]&c %message");

		node.getNode("messages", "motd").setComment("Allowed Variables: %player, %online, %maxplayers");
		node.getNode("messages", "motd").setValue(Lists.newArrayList("&6Welcome &c%player &6to the server.", "&6There are currently &c%online &6/ &c%maxplayers &6players online.", "&6Do &c/rules &6to read a list of rules."));
		node.getNode("messages", "rules").setValue(Lists.newArrayList("&c1: Do not grief.", "&c2: Do not swear.", "&c3: Respect everyone."));

		node.getNode("messages", "whitelist").setValue("&cThis server is currently whitelisted. Come back later.");
		node.getNode("messages", "banned").setComment("Allowed Variables: %reason, %banner");
		node.getNode("messages", "banned").setValue("&4You were banned from this server by %banner. &cReason: %reason");
		node.getNode("messages", "tempbanned").setComment("Allowed Variables: %reason, %banner %duration");
		node.getNode("messages", "tempbanned").setValue("&cYou were banned from this server by %banner. You will be unbanned in %6%duration&c. Reason: %reason");

		node.getNode("chat").setComment("Only use lowercase characters in the chat-section!\nAllowed Variables: %player, %message and all custom variables(Defaults: %prefix, %suffix)");
		node.getNode("chat", "style", "format").setComment("Possible Styles: general-format, variable-based");
		node.getNode("chat", "style", "format").setValue("general-format");
		node.getNode("chat", "style", "variable").setValue("prefix");

		node.getNode("chat", "variables").setComment("Here you can create your own custom variables.\nYou can set these variables in your permissions plugin.\nWith PermissionManager and PEX they can be set by adding options to a group or player.\nThe Chat system in the plugin will not work properly without a proper permissions plugin.");

		node.getNode("chat", "variables", "prefix", "text-before").setValue("");
		node.getNode("chat", "variables", "prefix", "text-after").setValue(" ");

		node.getNode("chat", "variables", "suffix", "text-before").setValue("");
		node.getNode("chat", "variables", "suffix", "text-after").setValue(" ");

		node.getNode("chat", "format", "general-format").setValue("%prefix&e%player %suffix&7: %message");

		node.getNode("chat", "format", "variable-based", "mod").setValue("%prefix&e%player %suffix&7: %message");
		node.getNode("chat", "format", "variable-based", "admin").setValue("%prefix&e%player %suffix&7: %message");
		node.getNode("chat", "format", "variable-based", "owner").setValue("%prefix&e%player %suffix&7: %message");

		return node;
	}

	public boolean isTeleportDelay() { return this.config.getNode("teleport", "delay", "enabled").getBoolean(); }
	public int getTeleportDelay() { return this.config.getNode("teleport", "delay", "seconds").getInt(); }
	public int getTeleportExpiry() { return this.config.getNode("teleport", "expiry", "seconds").getInt(); }

	@Nonnull public Text getBroadcastMessage(@Nonnull final String message) { return TextUtils.toText(this.config.getNode("messages", "broadcast").getString().replace("%message", message) + " "); }
	@Nonnull public Text getLoginMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "login").getString().replace("%player", playername)); }
	@Nonnull public Text getLogoutMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "logout").getString().replace("%player", playername)); }
	@Nonnull public List<Text> getMotd(@Nonnull final String playername, final int playersonline, final int maxplayers) { return this.config.getNode("messages", "motd").getList(obj -> TextUtils.toText(((String) obj).replace("%player", playername).replace("%online", String.valueOf(playersonline)).replace("%maxplayers", String.valueOf(maxplayers)))); }
	@Nonnull public List<Text> getRules() { return this.config.getNode("messages", "rules").getList(obj -> TextUtils.toText((String) obj)); }
	@Nonnull public Text getFirstJoinMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("messages", "firstjoin").getString().replace("%player", playername)); }
	@Nonnull public Text getWhitelistMessage() { return TextUtils.toText(this.config.getNode("messages", "whitelist").getString()); }
	@Nonnull public Text getBanMessage(@Nonnull final Text reason, @Nonnull final Text banner) { return TextUtils.toText(this.config.getNode("messages", "banned").getString().replace("%reason", TextUtils.toString(reason)).replace("%banner", TextUtils.toString(banner))); }
	@Nonnull public Text getTempBanMessage(@Nonnull final Text reason, @Nonnull final Text banner, @Nonnull final String duration) { return TextUtils.toText(this.config.getNode("messages", "tempbanned").getString().replace("%banner", TextUtils.toString(banner)).replace("%reason", TextUtils.toString(reason)).replace("%duration", duration)); }

	@Nonnull
	public String getChatMessage(@Nonnull final String player, @Nonnull final Subject subject, @Nonnull final String message) {
		String style = this.config.getNode("chat", "style", "format").getString();
		String format;

		if (style.equals("variable-based")) {
			String variable = this.config.getNode("chat", "style", "variable").getString();
			Optional<String> value = subject.getOption(variable);

			if (value.isPresent()) { format = this.getVariableChatFormat(value.get()).orElse(this.getGeneralChatFormat()); }
			else { format = this.getGeneralChatFormat(); }
		}
		else { format = this.getGeneralChatFormat(); }

		format = format.replace("%player", player).replace("%message", message);

		for (Object v : this.config.getNode("chat", "variables").getChildrenMap().keySet()) {
			String variable = (String) v;

			Optional<String> value = subject.getOption(variable);
			format = format.replace("%" + variable, value.isPresent() ? this.config.getNode("chat", "variables", variable, "text-before").getString() + value.get() + this.config.getNode("chat", "variables", variable, "text-after").getString() : "");
		}
		return format;
	}
	@Nonnull public String getGeneralChatFormat() { return this.config.getNode("chat", "format", "general-format").getString(); }
	@Nonnull public Optional<String> getVariableChatFormat(@Nonnull final String value) { return Optional.ofNullable(this.config.getNode("chat", "format", "variable-based", value).getString()); }

	@Nonnull
	public int getMaxHomes(@Nonnull final Player p) {
		Subject subject = p.getContainingCollection().get(p.getIdentifier());
		String homes = subject.getOption("home").orElse(subject.getOption("homes").orElse(""));

		if (homes.equals("")) { return 0; }
		else if (homes.equals("-1") || homes.equalsIgnoreCase("unlimited")) { return Integer.MAX_VALUE; }
		else {
			try { return Integer.parseInt(homes); }
			catch (final NumberFormatException exc) { return 0; }
		}
	}
}