package me.mrdaniel.crucialcraft.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

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
	public List<ItemStack> getKit(@Nonnull final String name) {
		List<ItemStack> l = Lists.newArrayList();

		this.config.getNode("kits", name, "items").getChildrenMap().forEach((id, data) -> super.getCrucialCraft().getGame().getRegistry().getType(ItemType.class, (String)id).ifPresent(type -> l.add(ItemStack.builder().itemType(type).quantity(data.getNode("amount").getInt()).build())));

		return l;
	}

	@Nonnull
	private CommentedConfigurationNode getEmptyNode() {
		CommentedConfigurationNode node = this.loader.createEmptyNode(ConfigurationOptions.defaults());

		node.getNode("kits").setComment("Kits can be edited or created here.\nThe time setting set the kit reload time in seconds.");
		node.getNode("kits", "default", "time").setValue(86400);
		node.getNode("kits", "default", "items", "minecraft:diamond_sword", "amount").setValue(1);
		node.getNode("kits", "default", "items", "minecraft:diamond_sword", "name").setValue("&c&lPenetrator");
		node.getNode("kits", "default", "items", "minecraft:diamond_sword", "lore").setValue(Lists.newArrayList("&2A very sharp sword.", "&2Will defeat anyone."));
		node.getNode("kits", "default", "items", "minecraft:diamond_sword", "enchantments", "minecraft:sharpness").setValue(1);
		node.getNode("kits", "default", "items", "minecraft:emerald", "amount").setValue(6);

		return node;
	}
}