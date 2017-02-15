package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class Kits extends CCObject {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode config;

	public Kits(@Nonnull final CrucialCraft cc, @Nonnull final Path path) {
		super(cc);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		CommentedConfigurationNode node = null;
		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to create kits file: {}", exc); }
		}
		else {
			try { node = loader.load(); }
			catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to load kits file: {}", exc); }
		}
		if (node == null) { this.config = this.getEmptyNode(); this.save(); }
		else { this.config = node; }
	}

	public void save() {
		try { this.loader.save(this.config); }
		catch (final IOException exc) { super.getCrucialCraft().getLogger().error("Failed to save kits file: {}", exc); }
	}

	@Nonnull
	public List<String> getKits() { return this.config.getNode("kits").getChildrenMap().keySet().stream().map(obj -> (String)obj).collect(Collectors.toList()); }

	@SuppressWarnings("serial")
	@Nonnull
	public List<ItemStack> getKit(@Nonnull final String name) {
		List<ItemStack> l = Lists.newArrayList();

		try { this.config.getNode("kits", name, "items").getValue(new TypeToken<List<ItemStack>>(){}).forEach(l::add); }
		catch (final ObjectMappingException exc) { super.getCrucialCraft().getLogger().error("Failed to get items: {}", exc); }

		return l;
	}

	public int getKitDelay(@Nonnull final String name) { return this.config.getNode("kits", name, "time").getInt(); }
	public boolean isKitPlaytime(@Nonnull final String name) { return this.config.getNode("kits", name, "playtime").getBoolean(); }

	@SuppressWarnings("serial")
	public void setKit(@Nonnull final String name, @Nonnull final List<ItemStack> items, final int time, final boolean playtime) {
		this.config.getNode("kits", name, "time").setValue(time);
		this.config.getNode("kits", name, "playtime").setValue(playtime);
		try { this.config.getNode("kits", name, "items").setValue(new TypeToken<List<ItemStack>>(){}, items); }
		catch (final ObjectMappingException exc) { super.getCrucialCraft().getLogger().error("Failed to save items: {}", exc); }
		this.save();
	}

	public void deleteKit(@Nonnull final String name) {
		this.config.getNode("kits").removeChild(name); this.save();
	}

	@Nonnull
	private CommentedConfigurationNode getEmptyNode() {
		CommentedConfigurationNode node = this.loader.createEmptyNode(ConfigurationOptions.defaults());

		node.getNode("kits").setComment("Kits can be edited, created or deleted here.\nThe time setting set the kit reload time in seconds.\nIf playtime is set to true, the kit delay will work based on ingame-time, else it will work based on system-time");

		return node;
	}
}