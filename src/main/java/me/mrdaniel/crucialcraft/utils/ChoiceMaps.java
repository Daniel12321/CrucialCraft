package me.mrdaniel.crucialcraft.utils;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;

import com.google.common.collect.Maps;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;

public class ChoiceMaps extends CCObject {

	private final HashMap<String, ItemType> itemtypes;
	private final HashMap<String, EntityType> entitytypes;
	private final HashMap<String, Enchantment> enchantments;

	public ChoiceMaps(@Nonnull final CrucialCraft cc) {
		super(cc);

		this.itemtypes = Maps.newHashMap();
		this.entitytypes = Maps.newHashMap();
		this.enchantments = Maps.newHashMap();

		super.getCrucialCraft().getGame().getRegistry().getAllOf(ItemType.class).forEach(type ->  this.itemtypes.put(type.getName().replaceAll("_", "").replaceAll("minecraft:", "").toLowerCase(), type));
		super.getCrucialCraft().getGame().getRegistry().getAllOf(EntityType.class).forEach(type ->  this.entitytypes.put(type.getName().replaceAll("_", "").replaceAll("minecraft:", "").toLowerCase(), type));
		super.getCrucialCraft().getGame().getRegistry().getAllOf(Enchantment.class).forEach(type ->  this.enchantments.put(type.getName().replaceAll("_", "").replaceAll("minecraft:", "").toLowerCase(), type));
	}

	@Nonnull public HashMap<String, ItemType> getItemTypes() { return this.itemtypes; }
	@Nonnull public HashMap<String, EntityType> getEnityTypes() { return this.entitytypes; }
	@Nonnull public HashMap<String, Enchantment> getEnchantments() { return this.enchantments; }
}