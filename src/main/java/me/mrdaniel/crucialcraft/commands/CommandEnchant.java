package me.mrdaniel.crucialcraft.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandEnchant extends PlayerCommand {

	public CommandEnchant(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.enchantment(cc, "type"), Argument.optional(Argument.integer("level")));
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		ItemStack hand = src.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));

		Enchantment type = args.get("type");
		int level = args.has("level") ? args.get("level") : 1;

		if ((!hand.supports(Keys.ITEM_ENCHANTMENTS) || !type.canBeAppliedToStack(hand) || level > type.getMaximumLevel()) && !src.hasPermission("cc.enchant.unsafe")) {
			throw new CommandException("You dont have permission to use unsafe enchantments.");
		}

		ItemEnchantment enchantment = new ItemEnchantment(type, level);
		List<ItemEnchantment> ench = hand.get(Keys.ITEM_ENCHANTMENTS).orElse(Lists.newArrayList());
		ench.removeIf(e -> e.getEnchantment() == type);
		ench.add(enchantment);
		hand.offer(Keys.ITEM_ENCHANTMENTS, ench);

		src.setItemInHand(HandTypes.MAIN_HAND, hand);
		src.sendMessage(Text.of(TextColors.GOLD, "You added ", TextColors.RED, type.getName(), " ", level, " to the item in your hand."));
	}

	@Override
	public String getPermission() {
		return "cc.enchant.safe";
	}

	@Override
	public String getName() {
		return "";
	}
}