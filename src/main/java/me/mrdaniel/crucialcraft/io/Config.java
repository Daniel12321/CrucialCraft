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

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.TextUtils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config extends CCObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode config;

	public Config(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
		super(cc);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { super.getCrucialCraft().getContainer().getAsset("config.conf").get().copyToFile(path); }
			catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to save config asset: {}", exc); }
		}

		this.config = this.load();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to load config: {}", exc); return this.loader.createEmptyNode(); }
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
	public String getChatMessage(@Nonnull final Text player, @Nonnull final Subject subject, @Nonnull final String message) {
		String style = this.config.getNode("chat", "style", "format").getString();
		String format;

		if (style.equals("variable-based")) {
			String variable = this.config.getNode("chat", "style", "variable").getString();
			Optional<String> value = subject.getOption(variable);

			if (value.isPresent()) { format = this.getVariableChatFormat(value.get()).orElse(this.getGeneralChatFormat()); }
			else { format = this.getGeneralChatFormat(); }
		}
		else { format = this.getGeneralChatFormat(); }

		format = format.replace("%player", TextUtils.toString(player)).replace("%message", message);

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