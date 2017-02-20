package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

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
	@Nonnull public Text getWhitelistMessage() { return TextUtils.toText(this.config.getNode("messages", "whitelist").getString()); }
	@Nonnull public Text getBanMessage(@Nonnull final Text reason, @Nonnull final Text banner) { return TextUtils.toText(this.config.getNode("messages", "banned").getString().replace("%reason", TextUtils.toString(reason)).replace("%banner", TextUtils.toString(banner))); }
	@Nonnull public Text getTempBanMessage(@Nonnull final Text reason, @Nonnull final Text banner, @Nonnull final String duration) { return TextUtils.toText(this.config.getNode("messages", "tempbanned").getString().replace("%banner", TextUtils.toString(banner)).replace("%reason", TextUtils.toString(reason)).replace("%duration", duration)); }

	@Nonnull public Text getFirstJoinMessage(@Nonnull final String playername) { return TextUtils.toText(this.config.getNode("firstjoin", "message").getString().replace("%player", playername)); }
	public boolean isFirstJoinKit() { return this.config.getNode("firstjoin", "kit", "enabled").getBoolean(); }
	@Nonnull public String getFirstJoinKit() { return this.config.getNode("firstjoin", "kit", "kit").getString(); }

	@Nonnull public String getChatStyle() { return this.config.getNode("chat", "style", "format").getString(); }
	@Nonnull public String getChatVariable() { return this.config.getNode("chat", "style", "variable").getString(); }
	@Nonnull public List<String> getVariables() { return this.config.getNode("chat", "variables").getChildrenMap().keySet().stream().map(obj -> (String)obj).collect(Collectors.toList()); }
	@Nonnull public String getVariableText(@Nonnull final String variable, @Nonnull final String value) { return this.config.getNode("chat", "variables", variable, "text-before").getString() + value + this.config.getNode("chat", "variables", variable, "text-after").getString(); }
	@Nonnull public String getGeneralChatFormat() { return this.config.getNode("chat", "format", "general-format").getString(); }
	@Nonnull public Optional<String> getVariableChatFormat(@Nonnull final String value) { return Optional.ofNullable(this.config.getNode("chat", "format", "variable-based", value).getString()); }

	public boolean isChatEnabled() { return this.config.getNode("modules", "chat").getBoolean(); }
//	public boolean isEconomyEnabled() { return this.config.getNode("modules", "economy").getBoolean(); }
	public boolean isHomesEnabled() { return this.config.getNode("modules", "homes").getBoolean(); }
	public boolean isJailsEnabled() { return this.config.getNode("modules", "jails").getBoolean(); }
	public boolean isKitsEnabled() { return this.config.getNode("modules", "kits").getBoolean(); }
	public boolean isWarpsEnabled() { return this.config.getNode("modules", "warps").getBoolean(); }
}